package Components;

import Utils.HttpUtils;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatChannels {
    String token;
    HttpUtils util;
    public ChatChannels(String token){
        this.token = token;
        this.util = new HttpUtils();
    }

    public String list_channels(String... strs) throws IOException {
        String url = "/chat/users/me/channels";
        return this.util.get_request(url, this.token);
    }

    public String create_channel(String... strs) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String url = "/chat/users/me/channels";
        HashMap map = new HashMap();
        List<HashMap> list = new ArrayList<>();
        int size = strs.length - 2 ;
        for (int i =0; i<size; i++){
            map.put("email", strs[i+2]);
            list.add(map);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", strs[0]);
        jsonObject.put("type", strs[1]);
        jsonObject.put("members", list);
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        return this.util.post_request(url, this.token, body);
    }

    public String get_channel(String... strs) throws IOException {
        String url = "/chat/channels/%s";
        url = String.format(url, strs);
        return this.util.get_request(url, this.token);
    }

}
