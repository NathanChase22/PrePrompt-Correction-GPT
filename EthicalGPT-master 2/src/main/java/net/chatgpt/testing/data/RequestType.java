package net.chatgpt.testing.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.chatgpt.testing.http.HttpUtil;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static net.chatgpt.testing.data.ChatGPTRequester.TOKEN;
import static net.chatgpt.testing.data.RequestCreator.GSON;

public enum RequestType {

    CHAT("https://api.openai.com/v1/chat/completions");

    private final String requestURL;

    RequestType(String requestURL) {
        this.requestURL = requestURL;
    }

    public CompletableFuture<String> makeRequest(RequestCreator.RequestData requestData) {

        requestData.header.put("Authorization", "Bearer " + TOKEN);
        requestData.header.put("Authorization", "Bearer " + TOKEN);

        return CompletableFuture.supplyAsync(() -> {
            String response;

            try {
                response = HttpUtil.post(requestData.requestType.requestURL,
                        HttpUtil.mapToStringEntity(requestData.body),
                        HttpUtil.createHeadersArray(requestData.header)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            JsonArray array = GSON.fromJson(response, JsonObject.class)
                    .get("choices").getAsJsonArray();

            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < array.size(); i++) {
                builder.append("RESPONSE 1\n\n")
                        .append(array.get(i).getAsJsonObject()
                        .get("message").getAsJsonObject()
                        .get("content").getAsString());
            }
            return builder.toString();
        });
    }

}
