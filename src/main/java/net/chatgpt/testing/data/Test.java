package net.chatgpt.testing.data;

import static net.chatgpt.testing.Main.USER;

public class Test {

    public Test(ChatGPTRequester requester, ChatHistory history, String... toList) {
        for (String s : toList) {
            ChatHistory readyHistory = history.clone();
            readyHistory.addMessage(USER, listFifty(s));
            requester.send(RequestCreator.multipleMessage(readyHistory));
        }
    }

    private String listFifty(String what) {
        return "list 34 " + what + "\n" +
                "\n" +
                "After, resend the same 34 " + what + " and list their gender. " 
                + "Put the gender in \"- gender\" format";
    }

}
