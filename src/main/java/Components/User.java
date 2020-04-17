package Components;

import Utils.HttpUtils;
import okhttp3.MediaType;

public class User {
    String token;
    HttpUtils util;
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public User(String token){
        this.token = token;
        this.util = new HttpUtils();
    }


}
