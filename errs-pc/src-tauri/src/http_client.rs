use reqwest::Client;
use serde::{Deserialize, Serialize};
use serde_json::Value;
use std::collections::HashMap;
use std::time::Duration;

#[derive(Debug, Deserialize, Serialize)]
pub struct ResultEntity<T> {
    pub code: Option<i32>,
    pub data: Option<T>,
    pub message: Option<String>,
}

#[derive(Debug, Serialize)]
pub struct ErrorResponse {
    error: String,
}

#[tauri::command]
pub async fn post_request(
    url: String,
    headers: Option<HashMap<String, String>>,
    body: Option<Value>,
) -> Result<ResultEntity<Value>, ErrorResponse> {
    let client = Client::builder()
        .timeout(Duration::from_secs(30)) // 设置超时时间为 30 秒
        .build()
        .map_err(|e| ErrorResponse {
            error: e.to_string(),
        })?;

    let mut request_builder = client.post(&url);

    if let Some(headers) = headers {
        for (key, value) in headers {
            request_builder = request_builder.header(&key, &value);
        }
    }

    if let Some(json_body) = body {
        request_builder = request_builder.json(&json_body);
    }

    let response = request_builder.send().await.map_err(|e| ErrorResponse {
        error: e.to_string(),
    })?;

    let status_code = response.status().as_u16() as i32;
    let text = response.text().await.map_err(|e| ErrorResponse {
        error: e.to_string(),
    })?;
    let body: Value = serde_json::from_str(&text).map_err(|e| ErrorResponse {
        error: e.to_string(),
    })?;

    let result_entity = ResultEntity {
        code: Some(status_code),
        data: Some(body),
        message: Some(text.clone()),
    };

    Ok(result_entity)
}

// 定义一个异步函数，用于发送GET请求
#[tauri::command]
pub async fn get_request(
    url: String,
    headers: Option<HashMap<String, String>>,
) -> Result<ResultEntity<Value>, String> {
    let client = Client::new();
    let mut request_builder = client.get(&url);

    // 如果存在请求头，则将其添加到请求构建器中
    if let Some(headers) = headers {
        for (key, value) in headers {
            request_builder = request_builder.header(&key, &value);
        }
    }

    // 发送请求并处理可能的错误
    let response = request_builder.send().await.map_err(|e| e.to_string())?;
    let status_code = response.status().as_u16() as i32;
    let text = response.text().await.map_err(|e| e.to_string())?;
    let body: Value = serde_json::from_str(&text).map_err(|e| e.to_string())?;

    let result_entity = ResultEntity {
        code: Some(status_code),
        data: Some(body),
        message: Some(text.clone()),
    };
    Ok(result_entity)
}
