package com.x.backend.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DoubanCrawlerTests {

    @Test
    void movieTop250Test() throws IOException {
        String url = "https://movie.douban.com/top250";
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36") // 更完整的UA
                .timeout(10000) // 10秒超时
                .get();

        Elements movies = doc.select(".item");
        int count = 0;

        for (Element movie : movies) {
            if (count >= 10) break; // 只取前10条

            // 更精准的标题选择器（避免获取到英文标题）
            String title = movie.select(".title").text();
            String rating = movie.select(".rating_num").text();


            // 数据清洗：移除无关字符
            rating = rating.replaceAll("[^\\d.]", "");

            System.out.printf("【%d】%s - 评分：%s%n",
                    count + 1, title, rating);

            count++;
        }
    }

    @Test
    void bookTop250Test() throws IOException {
        String url = "https://book.douban.com/top250";
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36") // 更完整的UA
                .timeout(10000) // 10秒超时
                .get();

        Elements books = doc.select(".item");
        int count = 0;

        for (Element book : books) {
            if (count >= 10) break; // 只取前10条

            String title = book.select(".pl2").text();
            String rating = book.select(".rating_nums").text();
            String inq = book.select(".inq").text();
            String pl = book.select(".pl").text();
            // 提取所有符合要求的图片
            Elements bookCovers = book.select("a.nbg > img");
            String href = book.select("a.nbg[href]").attr("abs:href");
            Document bookDoc = Jsoup.connect(href)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36") // 更完整的UA
                    .timeout(10000) // 10秒超时
                    .get();

            Elements introParagraphs = bookDoc.select(".intro p");
            List<String> validParagraphs = new ArrayList<>();

            for (Element p : introParagraphs) {
                // 1. 获取原始文本并清理
                String rawText = p.text();

                // 2. 执行深度清理
                String cleanedText = rawText
                        .trim()  // 去首尾空格
                        .replaceAll("[\\s\\u00A0]+", "")  // 替换所有空白符和&nbsp;为单个空格
                        .replaceAll("^\\s+|\\s+$", "");    // 二次确保首尾干净

                // 3. 过滤无效内容
                if (cleanedText.isEmpty()
                        || cleanedText.equals("(展开全部)")
                        || cleanedText.matches("^(展开全部).*")) {  // 匹配"展开更多"开头的内容
                    continue;
                }

                validParagraphs.add(cleanedText);
            }

            String formattedIntro;
            if (!validParagraphs.isEmpty()) {
                formattedIntro = String.join("\n", validParagraphs);
            } else {
                formattedIntro = "暂无简介";
            }

            System.out.println("处理后的简介：\n" + formattedIntro);

            for (Element img : bookCovers) {
                // 获取绝对地址（自动处理相对路径）
                String absSrc = img.absUrl("src");
                System.out.println("封面地址：" + absSrc);
            }

            // 数据清洗：移除无关字符
            rating = rating.replaceAll("[^\\d.]", "");

            System.out.println("【" + (count + 1) + "】：title:" + title + pl + "img:" + "-rating:" + rating + "inq:" + inq);

            count++;
        }
    }


}
