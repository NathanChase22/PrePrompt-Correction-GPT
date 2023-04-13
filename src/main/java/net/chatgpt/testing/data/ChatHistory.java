package net.chatgpt.testing.data;

import net.chatgpt.testing.data.json.MessageStructure;

import java.util.ArrayList;

public class ChatHistory extends ArrayList<MessageStructure> {

    public void addMessage(String role, String content) {
        add(new MessageStructure(role, content));
    }

    public MessageStructure[] getArray() {
        MessageStructure[] history = new MessageStructure[size()];
        for(int i = 0; i < size(); i++) {
            history[i] = get(i);
        }

        return history;
    }

    public float getEstimatedCost() {
        return (float) stream().mapToInt(ms -> ms.content.length()).sum() / 750f * .002f;
    }

    public ChatHistory clone() {
        return (ChatHistory) super.clone();
    }

}
