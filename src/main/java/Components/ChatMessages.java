package Components;

import Utils.HttpUtils;
import com.google.common.base.Joiner;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;


public class ChatMessages {
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String token;
    HttpUtils util;

    public ChatMessages(String token) {
        this.token = token;
        this.util = new HttpUtils();
    }

    public void listUserChatMessage(Map<String, String> queryMap) throws IOException {
        if (queryMap.size() == 0) return;
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
        System.out.println("Messages ids are : ");
        System.out.println(Arrays.toString(ids));
        System.out.println("Messages are : ");
        System.out.println(Arrays.toString(messages));
    }

    public void sendChatMessage(Map<String, String> bodyMap) throws IOException {
        String url = "/chat/users/me/messages";
        // Build post body
        JSONObject json = new JSONObject(bodyMap);
        RequestBody body = RequestBody.create(JSON, json.toString());
        this.util.postRequest(url, this.token, body);
    }

    public void updateChatMessage(String path, Map<String, String> bodyMap) throws IOException {
        String url = "/chat/users/me/messages/" + path;
        JSONObject json = new JSONObject(bodyMap);
        RequestBody body = RequestBody.create(JSON, json.toString());
        this.util.putRequest(url, this.token, body);
    }

    public void deleteChatMessage(String path, Map<String, String> queryMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/chat/users/me/messages/" + path + "?" + query;
        this.util.deleteRequest(url, this.token);
    }
}
