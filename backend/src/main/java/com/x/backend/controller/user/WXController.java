package com.x.backend.controller.user;

import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.HttpCodeConstants;
import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.user.entity.UserAccount;
import com.x.backend.pojo.user.vo.request.account.LoginVo;
import com.x.backend.service.user.AccountService;
import com.x.backend.util.EncryptUtils;
import com.x.backend.util.JWTUtils;
import com.x.backend.util.TimeUtils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil; // 引入 HttpUtil  (为了 HttpClientUtil.httpGet, 假设是hutool的)
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException; // 引入 UnsupportedEncodingException
import java.net.URLEncoder; // 引入正确的 URLEncoder
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/wx")
@RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
public class WXController {

    @Value("${wechat.token}")
    private String token;
    @Value("${wechat.app-id}")
    private String appId;
    @Value("${wechat.app-secret}")
    private String appSecret; // 补充 appSecret 的 Value 注解
    @Value("${wechat.redirect_uri}")
    private String redirectUri;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "userAccountService")
    private AccountService accountService;
    @Resource
    private JWTUtils<Account> jwtUtils;
    @Resource
    private EncryptUtils encryptUtils;
    @Resource
    private TimeUtils timeUtils;

    private ResultEntity<String> login(LoginVo loginVo) {
        if (loginVo == null || loginVo.getUsername() == null || loginVo.getPassword() == null
                || loginVo.getUsername().isEmpty() || loginVo.getPassword().isEmpty()) {
            return ResultEntity.failure(HttpMessageConstants.INFO_INCOMPLETE);
        }
        return accountService.login(loginVo);
    }

    /**
     * 微信消息验证接口
     */
    @GetMapping("/cheek")
    public String cheek(
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr) {
        log.info("signature: {}, timestamp: {}, nonce: {}, echostr: {}", signature, timestamp, nonce, echostr);
        if (checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return null;
    }

    /**
     * 校验签名
     */
    private boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] paramArr = new String[] { token, timestamp, nonce };
        Arrays.sort(paramArr);
        StringBuilder content = new StringBuilder();
        for (String param : paramArr) {
            content.append(param);
        }
        String ciphertext = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            ciphertext = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
        return ciphertext != null && ciphertext.equals(signature.toUpperCase());
    }

    /**
     * 字节数组转十六进制字符串
     */
    private static String byteToStr(byte[] byteArray) {
        StringBuilder strDigest = new StringBuilder();
        for (byte b : byteArray) {
            strDigest.append(byteToHexStr(b));
        }
        return strDigest.toString();
    }

    /**
     * 字节转十六进制字符串
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        return new String(tempArr);
    }

    /**
     * 生成二维码
     */
    @PostMapping("/getWechatQtCode")
    public Object getWechatQtCode(@RequestParam("mac_id") String mac_id) {
        JSONObject res = new JSONObject();
        try {
            redisTemplate.delete("wx:login:" + mac_id); // 清理缓存
            // 使用前端传入的mac地址作为state
            String redirectUriEncoded = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString())
                    .replaceAll("\\+", "%20");
            String oauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize" +
                    "?appid=" + appId +
                    "&redirect_uri=" + redirectUriEncoded +
                    "&response_type=code" +
                    "&scope=snsapi_userinfo" + // 使用 snsapi_userinfo 或 snsapi_base
                    "&state=" + mac_id +
                    "#wechat_redirect";

            res.set("key", mac_id);
            res.set("url", oauthUrl);
            log.info("mac_id: {}, redirectUriEncoded: {}, oauthUrl: {}", mac_id, redirectUriEncoded, oauthUrl);
            return res;
        } catch (UnsupportedEncodingException e) {
            log.error("URL编码失败", e);
            res.putOnce("error", "URL_ENCODING_ERROR");
            res.putOnce("message", "URL编码配置错误");
        } catch (Exception e) {
            log.error("二维码生成异常", e);
            res.putOnce("error", "SERVER_ERROR");
            res.putOnce("message", "服务端内部错误");
        }
        return res;
    }

    @GetMapping("/callback")
    public void wxCallback(
            @RequestParam("code") String code,
            @RequestParam("state") String macId) {

        redisTemplate.opsForValue().set(
                "wx:login:" + macId,
                code,
                5,
                TimeUnit.MINUTES);
    }

    @GetMapping("/checkLoginStatus")
    public ResultEntity<String> checkLoginStatus(@RequestParam String mac_id) {
        // 从 Redis 获取扫码后存储的临时 code
        String code = redisTemplate.opsForValue().get("wx:login:" + mac_id);
        if (code == null) {
            return ResultEntity.failure("未扫码");
        }
        log.info("code: {}", code);

        try {
            // 获取 access_token
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=" + appId +
                    "&secret=" + appSecret +
                    "&code=" + code +
                    "&grant_type=authorization_code";
            String tokenResponse = HttpUtil.get(url);
            JSONObject tokenRes = JSONUtil.parseObj(tokenResponse);

            // 检查微信返回的错误
            if (tokenRes.containsKey("errcode")) {
                log.error("微信接口错误: {}", tokenRes);
                return ResultEntity.failure("微信接口错误: " + tokenRes.getStr("errmsg"));
            }

            // 获取 access_token 和 openid
            String accessToken = tokenRes.getStr("access_token");
            String openid = tokenRes.getStr("openid");
            if (accessToken == null || openid == null) {
                log.error("微信 access_token 或 openid 获取失败: {}", tokenRes);
                return ResultEntity.failure("微信 access_token 或 openid 获取失败");
            }
            // 检查此用户是否已注册
            Account account = accountService.getByUsername(openid);
            if (account != null) {
                log.info(openid + " 已注册");
                // 清理缓存
                redisTemplate.delete("wx:login:" + mac_id);
                return login(new LoginVo(openid, openid + openid));
            }
            account = new Account();
            account.setUsername(openid);
            account.setPassword(encryptUtils.encryptPassword(openid + openid));
            account.setEmail("");
            account.setRole(RoleConstants.ROLE_USER);
            account.setCreatedAt(new Date());
            account.setIsBanned(false);

            ResultEntity<String> resultEntity = accountService.register(account);
            if (resultEntity.getCode() == HttpCodeConstants.OK) {
                log.info(code + " 登录成功");
                // 清理缓存
                redisTemplate.delete("wx:login:" + mac_id);
                return login(new LoginVo(openid, openid + openid));
            } else {
                return resultEntity;
            }

        } catch (Exception e) {
            log.error("用户信息获取失败 MAC:{}", mac_id, e);
            return ResultEntity.failure("系统错误");
        }
    }

}