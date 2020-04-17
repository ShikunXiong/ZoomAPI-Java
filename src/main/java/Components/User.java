package Components;

import Utils.HttpUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class User {
    String token;
    HttpUtils util;
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public User(String token){
        this.token = token;
        this.util = new HttpUtils();
    }

    public String listAllUsers(String... strs) throws IOException {
        String role_id = strs[0];
        String url = "/users" + "?role_id=" + role_id;
        String data = this.util.getRequest(url, this.token);
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        return jsonArray.toString();
    }

    public String createUsers(String... strs) throws IOException {
        String url = "/users";
        JSONObject json = new JSONObject();
        json.put("action", strs[0]);
        JSONObject user_info = new JSONObject();
        user_info.put("email", strs[1]);
        user_info.put("type", Integer.parseInt(strs[2]));
        json.put("user_info", user_info);
        RequestBody body = RequestBody.create(JSON, json.toString());
        return this.util.postRequest(url, this.token, body);
    }

    public String updateUser(String... strs) throws IOException {
        String url = "/users/%1$s" + "?login_type=%2$s";
        url = String.format(url, strs[0], strs[1]);
        JSONObject json = new JSONObject();
        json.put("first_name", strs[2]);
        json.put("last_name", strs[3]);
        json.put("type", Integer.parseInt(strs[4]));
        json.put("pmi", Integer.parseInt(strs[5]));
        json.put("phone_number", strs[6]);
        RequestBody body = RequestBody.create(JSON, json.toString());
        return this.util.postRequest(url, this.token, body);
    }

    public void deleteUser(String... strs) throws IOException {
        String url = "/users/me"
                   + "?action=%1$s"
                   + "&transfer_email=%2$s"
                   + "&transfer_meeting=%3$s"
                   + "&transfer_webinar=%4$s"
                   + "&transfer_recording=%5$s";
        url = String.format(url, strs);
        this.util.deleteRequest(url, this.token);
    }

    public String getUser(String... strs) throws IOException {
        String url = "/user/me" + "?login_type=%s";
        url = String.format(url, strs[0]);
        return this.util.getRequest(url, this.token);
    }
}
