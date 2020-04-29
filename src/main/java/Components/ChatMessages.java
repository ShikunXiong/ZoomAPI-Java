package Components;

import Utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ChatMessages {
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String token;
    HttpUtils util;

    public ChatMessages(String token) {
        this.token = token;
        this.util = new HttpUtils();
    }

    public String listUserChatMessage(Map<String, String> queryMap) throws IOException {
        if (queryMap.size() == 0) return "INVALID";
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        // Build query
        String url = "/chat/users/me/messages?" + query;
        // Parse data
        String data = this.util.getRequest(url, this.token);
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("messages");
        String[] ids = new String[jsonArray.length()];
        String[] messages = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = jsonArray.getJSONObject(i);
            ids[i] = jo.getString("id");
            messages[i] = jo.getString("message");
        }
        return Arrays.toString(messages);
    }

    public String listUserChatMessageID(Map<String, String> queryMap) throws IOException {
        if (queryMap.size() == 0) return "INVALID";
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        // Build query
        String url = "/chat/users/me/messages?" + query;
        // Parse data
        String data = this.util.getRequest(url, this.token);
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("messages");
        String[] ids = new String[jsonArray.length()];
        String[] messages = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = jsonArray.getJSONObject(i);
            ids[i] = jo.getString("id");
            messages[i] = jo.getString("message");
        }
        return Arrays.toString(ids);
    }

    public Map<String, String>[] listUserChatMessageAll(Map<String, String> queryMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        // Build query
        String url = "/chat/users/me/messages?" + query;
        // Parse data
        String data = this.util.getRequest(url, this.token);
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("messages");
        String token = jsonObject.getString("next_page_token");
        Map<String, String>[] maps = new HashMap[jsonArray.length() + 1];
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("next_page_token", token);
        maps[0] = tokenMap;
        for (int i = 1; i < jsonArray.length(); i++) {
            JSONObject jo = jsonArray.getJSONObject(i);
            Map<String, String> map = new HashMap<>();
            map.put("id", jo.getString("id"));
            map.put("message", jo.getString("message"));
            map.put("sender", jo.getString("sender"));
            map.put("date_time", jo.getString("date_time"));
            map.put("timestamp", String.valueOf(jo.getInt("timestamp")));
            maps[i] = map;
        }
        return maps;
    }

    public String sendChatMessage(Map<String, String> bodyMap) throws IOException {
        String url = "/chat/users/me/messages";
        // Build post body
        JSONObject json = new JSONObject(bodyMap);
        RequestBody body = RequestBody.create(JSON, json.toString());
        return this.util.postRequest(url, this.token, body);
    }

    public String updateChatMessage(String path, Map<String, String> bodyMap) throws IOException {
        String url = "/chat/users/me/messages/" + path;
        JSONObject json = new JSONObject(bodyMap);
        RequestBody body = RequestBody.create(JSON, json.toString());
        return this.util.putRequest(url, this.token, body);
    }

    public String deleteChatMessage(String path, Map<String, String> queryMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/chat/users/me/messages/" + path + "?" + query;
        return this.util.deleteRequest(url, this.token);
    }

}
