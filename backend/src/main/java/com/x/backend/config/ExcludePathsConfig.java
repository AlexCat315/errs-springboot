package com.x.backend.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExcludePathsConfig {

    @JsonProperty("excludePaths")
    private List<String> excludePaths;

}