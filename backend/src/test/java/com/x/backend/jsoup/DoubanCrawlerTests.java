package com.x.backend.jsoup;

import com.x.backend.mapper.admin.BookMapper;
import com.x.backend.util.MinioUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
public class DoubanCrawlerTests {

    @Resource
    private MinioUtils minioUtils;
    @Resource(name = "adminBookMapper")
    private BookMapper bookMapper;

    @Test
    void movieTop250Test() throws IOException {

    }

    @Test
    void bookTop250Test() throws Exception {
        // 循环10次处理分页 (0,25,50,...,225)
//        for (int start = 100; start < 250; start += 25) {
//            String url = "https://book.douban.com/top250?start=" + start;
//            Document doc = Jsoup.connect(url)
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
//                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
//                    .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
//                    .header("Accept-Encoding", "gzip, deflate, br")
//                    .referrer("https://www.douban.com/")
//                    .cookie("bid", "O9oqqZ0ROmQ")
//                    .cookie("dbcl2", "200217002:uSN8LZ2UiKk")
//                    .cookie("ll", "118377")
//                    .cookie("ap_v", "0,6.0")
//                    .cookie("viewed", "1007305_6518605_6082808_4913064_2035179")
//                    .cookie("push_doumail_num", "0")
//                    .cookie("push_noty_num", "0")
//                    .timeout(15000)
//                    .followRedirects(true)
//                    .execute()
//                    .parse();
//
//            Elements books = doc.select(".item");
//            System.out.println(books.size());
//            int count = 0;
//
//            for (Element book : books) {
////            if (count >= 2) break; // 只取前10条
//
//                String title = book.select(".pl2").text();
//                String rating = book.select(".rating_nums").text();
//                String inq = book.select(".inq").text();
//                String pl = book.select(".pl").text();
//                // 提取所有符合要求的图片
//                Elements bookCovers = book.select("a.nbg > img");
//                String href = book.select("a.nbg[href]").attr("abs:href");
//                Document bookDoc = Jsoup.connect(href)
//                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36") // 更完整的UA
//                        .timeout(10000) // 10秒超时
//                        .get();
//
//                Elements introParagraphs = bookDoc.select(".intro p");
//                List<String> validParagraphs = new ArrayList<>();
//
//                for (Element p : introParagraphs) {
//                    // 1. 获取原始文本并清理
//                    String rawText = p.text();
//
//                    // 2. 执行深度清理
//                    String cleanedText = rawText
//                            .trim()  // 去首尾空格
//                            .replaceAll("[\\s\\u00A0]+", "")  // 替换所有空白符和&nbsp;为单个空格
//                            .replaceAll("^\\s+|\\s+$", "");    // 二次确保首尾干净
//
//                    // 3. 过滤无效内容
//                    if (cleanedText.isEmpty()
//                            || cleanedText.equals("(展开全部)")
//                            || cleanedText.matches("^(展开全部).*")) {  // 匹配"展开更多"开头的内容
//                        continue;
//                    }
//
//                    validParagraphs.add(cleanedText);
//                }
//
//                String formattedIntro;
//                if (!validParagraphs.isEmpty()) {
//                    formattedIntro = String.join("\n", validParagraphs);
//                } else {
//                    formattedIntro = "暂无简介";
//                }
//
//                System.out.println("处理后的简介：\n" + formattedIntro);
//                String absSrc = "";
//                for (Element img : bookCovers) {
//                    // 获取绝对地址（自动处理相对路径）
//                    absSrc = img.absUrl("src");
//                    System.out.println("封面地址：" + absSrc);
//                    // 上传封面到 Minio
//                     absSrc = minioUtils.uploadCoverToMinio(absSrc);
//                }
//
//                // 数据清洗：移除无关字符
//                rating = rating.replaceAll("[^\\d.]", "");
//                String regex = "^(.*?)\\s*/.*?\\(\\s*(\\d+)人评价\\s*\\)";
//                System.out.println(pl);
//                Pattern pattern = Pattern.compile(regex);
//                Matcher matcher = pattern.matcher(pl);
//
//                if (matcher.find()) {
//                    String author = matcher.group(1);
//                    String ratingCount = matcher.group(2);
//
//                    System.out.println("著作人：" + author);
//                    System.out.println("评价人数：" + ratingCount);
//                    System.out.println("【" + (count + 1) + "】：title:" + title + "img:" + "-rating:" + rating + "inq:" + inq);
//                    Book book1 = new Book();
//                    book1.setName(title);
//                    book1.setAuthor(author);
//                    book1.setRating(Float.valueOf(rating));
//                    book1.setDescription(formattedIntro);
//                    book1.setIntroduction(inq);
//                    book1.setUsers(Long.valueOf(ratingCount));
//                    String img = "https://www.alexcat.it.com/minio-api/public-errs/" + absSrc;
//                    book1.setImg(img);
//                    bookMapper.insertBook(book1);
//                } else {
//                    System.out.println("未匹配到信息！");
//                }
//
//                count++;
//            }
//        }
    }


}
