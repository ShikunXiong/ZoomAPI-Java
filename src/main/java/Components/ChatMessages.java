package Components;

import Utils.HttpUtils;

public class ChatMessages {
    String token;
    HttpUtils util;
    public ChatMessages(String token){
        this.token = token;
        this.util = new HttpUtils();
    }
}
