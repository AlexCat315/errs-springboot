package com.x.backend.constructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.config.ExcludePathsConfig;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class PathExcludeConstructor {

    private List<String> excludedPaths;

    @PostConstruct
    public void init() throws IOException {
        // 加载JSON配置文件
        ClassPathResource resource = new ClassPathResource("exclude-paths.json");
        ObjectMapper objectMapper = new ObjectMapper();
        // 解析JSON文件为Java对象
        ExcludePathsConfig config = objectMapper.readValue(resource.getInputStream(), ExcludePathsConfig.class);
        excludedPaths = config.getExcludePaths();
    }

    public boolean excludePath(String url) {
        for (String path : excludedPaths) {
            if (url.startsWith(path)) {
                return true;
            }
        }
        return false;
    }
}
