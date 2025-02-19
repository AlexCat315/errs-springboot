package com.x.backend.controller.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.RoleConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/user/ai")
@RoleSecurity(RoleConstants.ROLE_USER)
public class AIController {

    private static final String API_URL = "https://api.deepseek.com/chat/completions";
    private static final int SSE_TIMEOUT = 180_000; // 3分钟超时
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${AI.deepseek.api-key}")
    private String API_KEY;


    @GetMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(
            @RequestParam @NotBlank(message = "问题不能为空") String question) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 构建请求
            HttpPost request = new HttpPost(API_URL);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + API_KEY);

            // 构建请求体
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", "deepseek-chat");
            requestBody.put("stream", false); // 非流式请求

            ArrayNode messages = objectMapper.createArrayNode();
            // 添加系统消息
            messages.add(objectMapper.createObjectNode()
                    .put("role", "system")
                    .put("content", "You are a helpful assistant specialized in recommending entertainment content."));
            // 添加用户消息
            messages.add(objectMapper.createObjectNode()
                    .put("role", "user")
                    .put("content", question));
            requestBody.set("messages", messages);

            request.setEntity(new StringEntity(requestBody.toString(), StandardCharsets.UTF_8));

            // 执行请求
            try (CloseableHttpResponse response = client.execute(request)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new IOException("API request failed: " + response.getStatusLine());
                }

                // 读取响应
                String responseContent = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));

                // 解析响应
                JsonNode jsonResponse = objectMapper.readTree(responseContent);
                String answer = jsonResponse.get("choices")
                        .get(0)
                        .get("message")
                        .get("content")
                        .asText();

                // 返回结果
                Map<String, String> result = new HashMap<>();
                result.put("answer", answer);
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("Chat error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> chatStream(
            @RequestParam @NotBlank(message = "问题不能为空") String question) {

        return ResponseEntity.ok().body(outputStream -> {
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpPost request = new HttpPost(API_URL);
                request.setHeader("Content-Type", "application/json");
                request.setHeader("Authorization", "Bearer " + API_KEY);

                // 构建请求体,设置stream为true
                ObjectNode requestBody = objectMapper.createObjectNode();
                requestBody.put("model", "deepseek-chat");
                requestBody.put("stream", true);

                ArrayNode messages = objectMapper.createArrayNode();
                messages.add(objectMapper.createObjectNode()
                        .put("role", "system")
                        .put("content", "You are a helpful assistant specialized in recommending entertainment content."));
                messages.add(objectMapper.createObjectNode()
                        .put("role", "user")
                        .put("content", question));
                requestBody.set("messages", messages);

                request.setEntity(new StringEntity(requestBody.toString(), StandardCharsets.UTF_8));

                try (CloseableHttpResponse response = client.execute(request)) {
                    if (response.getStatusLine().getStatusCode() != 200) {
                        throw new IOException("API request failed: " + response.getStatusLine());
                    }

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(response.getEntity().getContent()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("data: ")) {
                            String jsonData = line.substring(6);
                            if ("[DONE]".equals(jsonData)) {
                                break;
                            }

                            JsonNode jsonResponse = objectMapper.readTree(jsonData);
                            String content = jsonResponse.get("choices")
                                    .get(0)
                                    .get("delta")
                                    .get("content")
                                    .asText("");

                            outputStream.write(("data: " + content + "\n\n").getBytes());
                            outputStream.flush();
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Chat stream error", e);
                String errorMessage = "data: {\"error\": \"" + e.getMessage() + "\"}\n\n";
                outputStream.write(errorMessage.getBytes());
                outputStream.flush();
            }
        });
    }
}