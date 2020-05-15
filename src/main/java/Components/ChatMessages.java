package Components;

import Utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.gson.Gson;
import database.DbHelper;
import models.Message;
import okhttp3.Connection;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
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

    public Map<String, String>[] listUserChatMessageAll(Map<String, String> queryMap) throws IOException, SQLException {
        /*
        queryMap :
            to_channel : channel ID
         */
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
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = jsonArray.getJSONObject(i);
            Map<String, String> map = new HashMap<>();
            map.put("id", jo.getString("id"));
            map.put("message", jo.getString("message"));
            map.put("sender", jo.getString("sender"));
            map.put("date_time", jo.getString("date_time"));
            map.put("timestamp", String.valueOf(jo.getInt("timestamp")));
            // Store the message into the database
            Message m = new Message(0, map.get("id"), map.get("message"), map.get("sender"), map.get("date_time"), jo.getInt("timestamp"), queryMap.getOrDefault("to_channel", "NO CHANNEL!!!"));
            DbHelper<Message> messageDbHelper = null;
            try {
                messageDbHelper = DbHelper.getConnection();
                messageDbHelper.update(m);
            } catch (SQLException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                messageDbHelper.closeConnection();
            }
            maps[i+1] = map;
        }
        return maps;
    }

    public String sendChatMessage(Map<String, String> bodyMap) throws IOException, SQLException, InterruptedException {
        /*
        bodyMap -
        message : message
        to_channel : channelId
         */
        String url = "/chat/users/me/messages";
        // Build post body
        JSONObject json = new JSONObject(bodyMap);
        RequestBody body = RequestBody.create(JSON, json.toString());
        String status = this.util.postRequest(url, this.token, body);
        String channelID = bodyMap.get("to_channel");
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("to_channel", channelID);
        Thread.sleep(3000);
        listUserChatMessageAll(queryMap);
        return status;
    }

    public String updateChatMessage(String path, Map<String, String> bodyMap) throws IOException, SQLException {
        String url = "/chat/users/me/messages/" + path;
        JSONObject json = new JSONObject(bodyMap);
        RequestBody body = RequestBody.create(JSON, json.toString());
        String status =  this.util.putRequest(url, this.token, body);
        DbHelper<Message> messageDbHelper = null;
        try {
            messageDbHelper = DbHelper.getConnection();
            messageDbHelper.update(Message.class, path, "message", bodyMap.get("message"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            messageDbHelper.closeConnection();
        }
        return status;
    }

    public String deleteChatMessage(String path, Map<String, String> queryMap) throws IOException, SQLException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/chat/users/me/messages/" + path + "?" + query;
        String status = this.util.deleteRequest(url, this.token);
        DbHelper<Message> messageDbHelper = null;
        try {
            messageDbHelper = DbHelper.getConnection();
            messageDbHelper.delete(Message.class,"messageId", path);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            messageDbHelper.closeConnection();
        }
        return status;
    }

}
