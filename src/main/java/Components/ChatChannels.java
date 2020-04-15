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
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public ChatChannels(String token){
        this.token = token;
        this.util = new HttpUtils();
    }
    public String list_channels(String... strs) throws IOException {
        String url = "/chat/users/me/channels";
        return this.util.get_request(url, this.token);
    }

    public String create_channel(String... strs) throws IOException {

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

    public String update_channel(String... strs) throws IOException {
        String url = "/chat/channels/%s";
        url = String.format(url, strs[0]);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", strs[1]);
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        return this.util.patch_request(url, this.token, body);
    }

    public String delete_channel(String... strs) throws IOException {
        String url = "/chat/channels/%s";
        url = String.format(url, strs[0]);
        return this.util.delete_request(url, this.token);
    }

    public String list_channel_members(String... strs) throws IOException {
        String url = "/chat/channels/%s/members";
        url = String.format(url, strs[0]);
        return this.util.get_request(url, this.token);
    }

    public String invite_members(String... strs) throws IOException {
        String url = "/chat/channels/%s/members";
        url = String.format(url, strs[0]);
        HashMap map = new HashMap();
        List<HashMap> list = new ArrayList<>();
        int size = strs.length - 1 ;
        for (int i =0; i<size; i++){
            map.put("email", strs[i+1]);
            list.add(map);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("members", list);
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        return this.util.post_request(url, this.token, body);
    }

    public String join_channel(String... strs) throws IOException {
        String url = "/chat/channels/%s/members/me";
        url = String.format(url, strs[0]);
        return this.util.post_request(url, this.token, null);
    }

    public String leave_channel(String... strs) throws IOException {
        String url = "/chat/channels/%s/members/me";
        url = String.format(url, strs[0]);
        return this.util.delete_request(url, this.token);
    }

    public String remove_member(String... strs) throws IOException {
        String url = "/chat/channels/%s/members/%s";
        url = String.format(url, strs[0], strs[1]);
        return this.util.delete_request(url, this.token);
    }
}
