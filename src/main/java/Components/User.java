package Components;

import Utils.HttpUtils;
import com.google.common.base.Joiner;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class User {
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String token;
    HttpUtils util;

    public User(String token) {
        this.token = token;
        this.util = new HttpUtils();
    }

    public String listAllUsers(Map<String, String> queryMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/users?" + query;
        String data = this.util.getRequest(url, this.token);
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        return jsonArray.toString();
    }

    public String createUsers(Map<String, String> bodyMap) throws IOException {
        String url = "/users";
        JSONObject json = new JSONObject(bodyMap);
        RequestBody body = RequestBody.create(JSON, json.toString());
        return this.util.postRequest(url, this.token, body);
    }

    public String updateUser(Map<String, String> queryMap, Map<String, String> bodyMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/users/me?" + query;
        JSONObject json = new JSONObject(bodyMap);
        RequestBody body = RequestBody.create(JSON, json.toString());
        return this.util.postRequest(url, this.token, body);
    }

    public void deleteUser(Map<String, String> queryMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/users/me?" + query;
        this.util.deleteRequest(url, this.token);
    }

    public String getUser(Map<String, String> queryMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/user/me?" + query;
        return this.util.getRequest(url, this.token);
    }
}
