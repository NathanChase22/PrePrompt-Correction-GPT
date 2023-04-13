package net.chatgpt.testing.data;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static net.chatgpt.testing.Main.SYSTEM;
import static net.chatgpt.testing.Main.USER;

public class RequestCreator {

    public static final String MODEL = "gpt-3.5-turbo";
    public static final String SYSTEM_MESSAGE = "You are a helpful assistant.";
    public static Gson GSON = new Gson();

    public static RequestData singleMessage(String message) {
        RequestData requestData = new RequestData(RequestType.CHAT);

        ChatHistory chatHistory = new ChatHistory();
        chatHistory.addMessage(SYSTEM, SYSTEM_MESSAGE);
        chatHistory.addMessage(USER, message);

        requestData.body.put("model", MODEL);
        requestData.body.put("messages", chatHistory.getArray());
        requestData.estimatedCost = chatHistory.getEstimatedCost();

        return requestData;
    }

    public static RequestData multipleMessage(ChatHistory chatHistory) {

        RequestData requestData = new RequestData(RequestType.CHAT);
        requestData.body.put("model", MODEL);
        requestData.body.put("messages", chatHistory.getArray());
        requestData.estimatedCost = chatHistory.getEstimatedCost();

        return requestData;
    }

    public static class RequestData {

        public RequestType requestType;

        public Map<String, String> header = new HashMap<>();
        public Map<String, Object> body = new HashMap<>();
        public float estimatedCost;

        public RequestData(RequestType requestType) {
            this.requestType = requestType;
        }

    }

}
