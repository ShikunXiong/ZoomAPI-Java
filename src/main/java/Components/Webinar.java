package Components;

import Utils.HttpUtils;
import com.google.common.base.Joiner;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class Webinar {
    String token;
    HttpUtils util;
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public Webinar(String token){
        this.token = token;
        this.util = new HttpUtils();
    }

    public String listWebinars(Map<String, String> queryMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/users/me/webinars?" + query;
        String data = this.util.getRequest(url, this.token);
        JSONObject json = new JSONObject(data);
        JSONArray jsonArray = json.getJSONArray("webinars");
        return jsonArray.toString();
    }

    public void createWebinar(Map<String, String> bodyMap) throws IOException {
        String url = "/users/me/webinars";
        JSONObject json = new JSONObject(bodyMap);
        RequestBody body = RequestBody.create(JSON, json.toString());
        this.util.postRequest(url, this.token, body);
    }

    public void updateWebinar(String path, Map<String, String> queryMap, Map<String, String> bodyMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/webinars/" + path + "?" + query;
        JSONObject json = new JSONObject(bodyMap);
        RequestBody body = RequestBody.create(JSON, json.toString());
        this.util.patchRequest(url, this.token, body);
    }

    public void deleteWebinar(String path, Map<String, String> queryMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/webinars/" + path + "?" + query;
        this.util.deleteRequest(url, this.token);
    }

    public void endWebinar(String path) throws IOException {
        String url = "/webinars/" + path + "/status";
        JSONObject json = new JSONObject();
        json.put("action", "end");
        RequestBody body = RequestBody.create(JSON, json.toString());
        this.util.putRequest(url, this.token, body);
    }

    public String getWebinars(String path, Map<String, String> queryMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/webinars/" + path + "?" + query;
        return this.util.getRequest(url, this.token);
    }

    public void addWebinarRegistrant(String path, Map<String, String> queryMap, Map<String, String> bodyMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/webinars/" + path + "/registrants" + query;
        JSONObject json = new JSONObject(bodyMap);
        RequestBody body = RequestBody.create(JSON, json.toString());
        this.util.postRequest(url, this.token, body);
    }
}
