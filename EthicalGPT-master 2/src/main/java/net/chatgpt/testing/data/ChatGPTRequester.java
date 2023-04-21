package net.chatgpt.testing.data;

import net.chatgpt.testing.Main;

public class ChatGPTRequester {

    public static final String TOKEN = "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";
    public static boolean DEBUG_MODE = true;
    public static int requestCounter = 0;

    private final RequestType requestType;

    public ChatGPTRequester(RequestType requestType) {
        this.requestType = requestType;
    }

    public void send(RequestCreator.RequestData requestData) {

        if(DEBUG_MODE) {
            requestCounter++;
            return;
        }

        Main.requestQueue.createRequest(() -> requestType.makeRequest(requestData), requestData.estimatedCost);
    }

}