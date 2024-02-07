package org.example.chatglm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class ChatGLM6B {


    private static final String URL = "http://127.0.0.1:8000";

    /**
     * 这里的ak和sk可以从百川官网获取，文章中已经演示过了，直接替换掉即可
     */
    private static final String API_KEY = "447b23c7367f819e0d0d49b5017dbb4d";
    private static final String SECRET_KEY = "+DkW3T6EYu2+x3oFMFf0vn2+UQE=";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static volatile OkHttpClient HTTP_CLIENT;

    public static String ask(String question, String knowledge) {
        try {
            String prompt = getPrompt(question, knowledge);
            return llmRequest(prompt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String llmRequest(String prompt) throws IOException {
        String requestData = getChatGLMRequest(prompt);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = calculateMd5(SECRET_KEY + requestData + timestamp);

        Headers headers = getHeaders(timestamp, signature);

        RequestBody body = RequestBody.create(requestData, MediaType.parse("application/json; charset=utf-8"));
        Request request = (new Request.Builder()).url(URL).headers(headers).post(body).build();

        try (Response response = getHttpClient().newCall(request).execute()) {
            JsonNode node = null;
            if (response.body() != null) {
                node = MAPPER.readTree(response.body().string());
            }
            if (node != null) {
                return node.get("response").asText();
            }
            return null;
        }
    }

    private static Headers getHeaders(String timestamp, String signature) {
        return (new Headers.Builder())
                .add("Content-Type", "application/json")
                .add("Authorization", "Bearer " + API_KEY)
                .add("X-BC-Request-Id", "RequestId-1001")
                .add("X-BC-Timestamp", timestamp)
                .add("X-BC-Signature", signature)
                .add("X-BC-Sign-Algo", "MD5")
                .build();
    }

    public static String calculateMd5(String inputString) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(inputString.getBytes());
            byte[] digest = md.digest();
            StringBuilder buffer = new StringBuilder();
            for (byte b : digest) {
                buffer.append(String.format("%02x", b & 0xff));
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getChatGLMRequest(String prompt) throws JsonProcessingException {
        ObjectNode data = JsonNodeFactory.instance.objectNode();
        data.put("prompt", prompt);

        data.put("history", JsonNodeFactory.instance.arrayNode());
        return new ObjectMapper().writeValueAsString(data);
    }

    private static String getPrompt(String question, String knowledge) throws JsonProcessingException {
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode obj = factory.objectNode();
        obj.put("请回答问题", question);
        obj.put("背景知识如下", knowledge);
        return new ObjectMapper().writeValueAsString(obj);
    }

    @SuppressWarnings("KotlinInternalInJava")
    synchronized private static OkHttpClient getHttpClient() {
        if (HTTP_CLIENT == null) {
            HTTP_CLIENT = (new OkHttpClient.Builder())
                    .connectTimeout(2L, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool(10, 5L, TimeUnit.MINUTES))
                    .build();
        }
        return HTTP_CLIENT;
    }

    public static void main(String[] args) {
        String llmResult = ChatGLM6B.ask("冲压机红灯故障的原因？", "冲压机红灯故障的维修方法");
        System.out.println("---->LLM回答结果：");
        System.out.println(llmResult);
    }

}
