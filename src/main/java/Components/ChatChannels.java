package Components;

import Utils.HttpUtils;
import com.google.common.base.Joiner;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import com.alibaba.fastjson.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatChannels {
    String token;
    HttpUtils util;
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public ChatChannels(String token){
        this.token = token;
        this.util = new HttpUtils();
    }
    public String listChannels(String... strs) throws IOException {
        String url = "/chat/users/me/channels";
        return this.util.getRequest(url, this.token);
    }

    public String createChannel(String... strs) throws IOException {

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
        return this.util.postRequest(url, this.token, body);
    }

    public String getChannel(String... strs) throws IOException {
        String url = "/chat/channels/%s";
        url = String.format(url, strs);
        return this.util.getRequest(url, this.token);
    }

    public String updateChannel(String... strs) throws IOException {
        String url = "/chat/channels/%s";
        url = String.format(url, strs[0]);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", strs[1]);
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        return this.util.patchRequest(url, this.token, body);
    }

    public String deleteChannel(String... strs) throws IOException {
        String url = "/chat/channels/%s";
        url = String.format(url, strs[0]);
        return this.util.deleteRequest(url, this.token);
    }

    public String listChannelMembers(String str, Map<String, String> queryMap) throws IOException {
        String url = "/chat/channels/%s/members";
        url = String.format(url, str);
        if (queryMap!=null) {
            String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
            url = url + "?" + query;
        }
        return this.util.getRequest(url, this.token);
    }

    public String inviteMembers(String... strs) throws IOException {
        String url = "/chat/channels/%s/members";
        url = String.format(url, strs[0]);
        HashMap<String, String> map = new HashMap<>();
        List<HashMap<String, String>> list = new ArrayList<>();
        int size = strs.length - 1 ;
        for (int i =0; i<size; i++){
            map.put("email", strs[i+1]);
            list.add(map);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("members", list);
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        return this.util.postRequest(url, this.token, body);
    }

    public String joinChannel(String... strs) throws IOException {
        String url = "/chat/channels/%s/members/me";
        url = String.format(url, strs[0]);
        RequestBody body = RequestBody.create(null, "");
        return this.util.postRequest(url, this.token, body);
    }

    public String leaveChannel(String... strs) throws IOException {
        String url = "/chat/channels/%s/members/me";
        url = String.format(url, strs[0]);
        return this.util.deleteRequest(url, this.token);
    }

    public String removeMember(String... strs) throws IOException {
        String url = "/chat/channels/%s/members/%s";
        url = String.format(url, strs[0], strs[1]);
        return this.util.deleteRequest(url, this.token);
    }

    public String getChannelIdByName(String name) throws IOException {
        String result = "";
        String response = this.listChannels();
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONArray array = jsonObject.getJSONArray("channels");
        for (int i=0; i< array.size(); i++){
            JSONObject object = (JSONObject)array.get(i);
            String channel_name = object.getString("name");
            if (channel_name.equals(name)){
                return object.getString("id");
            }
        }
        return result;
    }


}
