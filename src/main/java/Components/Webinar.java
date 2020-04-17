package Components;

import Utils.HttpUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Webinar {
    String token;
    HttpUtils util;
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public Webinar(String token){
        this.token = token;
        this.util = new HttpUtils();
    }

    public String listWebinars(String... strs) throws IOException {
        String url = "/users/me/webinars";
        String data = this.util.getRequest(url, this.token);
        JSONObject json = new JSONObject(data);
        JSONArray jsonArray = json.getJSONArray("webinars");
        return jsonArray.toString();
    }

    public void createWebinar(String... strs) throws IOException {
        String url = "/users/me/webinars";
        JSONObject json = new JSONObject();
        json.put("topic", strs[0]);
        json.put("type", Integer.parseInt(strs[1]));
        json.put("start_time", strs[2]);
        json.put("duration", Integer.parseInt(strs[3]));
        json.put("timezone", strs[4]);
        json.put("password", strs[5]);
        json.put("agenda", strs[6]);
        RequestBody body = RequestBody.create(JSON, json.toString());
        this.util.postRequest(url, this.token, body);
    }

    public void updateWebinar(String... strs) throws IOException {
        String url = "/webinars/%1$s" + "?occurrence_id=%2$s";
        url = String.format(url, strs[0], strs[1]);
        JSONObject json = new JSONObject();
        json.put("topic", strs[0]);
        json.put("type", Integer.parseInt(strs[1]));
        json.put("start_time", strs[2]);
        RequestBody body = RequestBody.create(JSON, json.toString());
        this.util.patchRequest(url, this.token, body);
    }

    public void deleteWebinar(String... strs) throws IOException {
        String url = "/webinars/%1$s" + "?occurrence_id=%2$s";
        url = String.format(url, strs[0], strs[1]);
        this.util.deleteRequest(url, this.token);
    }

    public void endWebinar(String... strs) throws IOException {
        String url = "/webinars/%s/status";
        url = String.format(url, strs[0]);
        JSONObject json = new JSONObject();
        json.put("action", "end");
        RequestBody body = RequestBody.create(JSON, json.toString());
        this.util.putRequest(url, this.token, body);
    }

    public String getWebinars(String... strs) throws IOException {
        String url = "/webinars/%1$s" + "?occurrence_id=%2$s";
        url = String.format(url, strs[0], strs[1]);
        return this.util.getRequest(url, this.token);
    }

    public void addWebinarRegistrant(String... strs) throws IOException {
        String url = "/webinars/%1$s/registrants" + "?occurrence_id=%2$s";
        url = String.format(url, strs[0], strs[1]);
        JSONObject json = new JSONObject();
        json.put("email", strs[0]);
        json.put("first_name", strs[1]);
        json.put("last_name", strs[2]);
        RequestBody body = RequestBody.create(JSON, json.toString());
        this.util.postRequest(url, this.token, body);
    }
}
