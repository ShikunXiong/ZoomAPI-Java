package Components;

import Utils.HttpUtils;
import database.DbHelper;
import models.Channel;
import models.ChannelsMembership;
import models.Message;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import com.alibaba.fastjson.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatChannels {
    private final String token;
    private final HttpUtils util;
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public ChatChannels(String token){
        this.token = token;
        this.util = new HttpUtils();
    }

    public String check() throws IOException {
        String url = "/chat/users/me/channels";
        return this.util.getRequest(url, this.token);
    }

    public String listChannels(String... strs) throws IOException, SQLException {
        String url = "/chat/users/me/channels";
        String data =  this.util.getRequest(url, this.token);
        JSONObject jsonObject = JSONObject.parseObject(data);
        JSONArray array = jsonObject.getJSONArray("channels");
        for (int i=0; i< array.size(); i++){
            JSONObject jo = (JSONObject)array.get(i);
            Channel c = new Channel(0, jo.getString("id"), jo.getString("jid"), jo.getString("name"), jo.getInteger("type"));
            DbHelper<Channel> channelDbHelper = null;
            try {
                channelDbHelper = DbHelper.getConnection();
                channelDbHelper.update(c);
            } catch (SQLException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                channelDbHelper.closeConnection();
            }
        }
        return data;
    }

    public String createChannel(String... strs) throws IOException, SQLException {

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
        String data =  this.util.postRequest(url, this.token, body);
        JSONObject jo = JSONObject.parseObject(data);
        Channel c = new Channel(0, jo.getString("id"), jo.getString("jid"), jo.getString("name"), jo.getInteger("type"));
        DbHelper<Channel> channelDbHelper = null;
        try {
            channelDbHelper = DbHelper.getConnection();
            channelDbHelper.write(c);
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            channelDbHelper.closeConnection();
        }
        return data;
    }

    public String getChannel(String... strs) throws IOException {
        String url = "/chat/channels/%s";
        url = String.format(url, strs);
        return this.util.getRequest(url, this.token);
    }

    public String updateChannel(String... strs) throws IOException, SQLException {
        String url = "/chat/channels/%s";
        url = String.format(url, strs[0]);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", strs[1]);
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        String status =  this.util.patchRequest(url, this.token, body);
        if (!status.equals("204")) {
            System.out.println("No permission to update this channel");
            return "FAIL";
        }
        DbHelper<Channel> channelDbHelper = DbHelper.getConnection();
        try {
            channelDbHelper = DbHelper.getConnection();
            channelDbHelper.update(Channel.class, strs[0], "channelName", strs[1]);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            channelDbHelper.closeConnection();
        }
        return status;
    }

    public String deleteChannel(String... strs) throws IOException, SQLException {
        String url = "/chat/channels/%s";
        url = String.format(url, strs[0]);
        String status =  this.util.deleteRequest(url, this.token);
        if (!status.equals("204")) {
            System.out.println("No permission to delete this channel");
            return "FAIL";
        }
        DbHelper<Channel> channelDbHelper = DbHelper.getConnection();
        try {
            channelDbHelper = DbHelper.getConnection();
            channelDbHelper.delete(Channel.class, "channelId", strs[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            channelDbHelper.closeConnection();
        }
        return status;
    }

    public String listChannelMembers(String... strs) throws IOException, SQLException {
        String url = "/chat/channels/%s/members";
        url = String.format(url, strs[0]);
        String data =  this.util.getRequest(url, this.token);
        JSONObject jsonObject = JSONObject.parseObject(data);
        JSONArray array = jsonObject.getJSONArray("members");
        for (int i=0; i< array.size(); i++){
            JSONObject jo = (JSONObject)array.get(i);
            ChannelsMembership c = new ChannelsMembership(0, strs[0], jo.getString("id"), jo.getString("email"), jo.getString("first_name"), jo.getString("last_name"), jo.getString("role"));
            DbHelper<ChannelsMembership> channelsMembershipDbHelper = null;
            try {
                channelsMembershipDbHelper = DbHelper.getConnection();
                channelsMembershipDbHelper.updateByField(c, "memberId", c.getMemberId());
            } catch (SQLException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                channelsMembershipDbHelper.closeConnection();
            }
        }
        return data;
    }

    public String inviteMembers(String... strs) throws IOException, SQLException {
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
        String status =  this.util.postRequest(url, this.token, body);
        listChannelMembers(strs[0]);
        return status;
    }

    public String joinChannel(String... strs) throws IOException, SQLException {
        String url = "/chat/channels/%s/members/me";
        url = String.format(url, strs[0]);
        RequestBody body = RequestBody.create(null, "");
        String status = this.util.postRequest(url, this.token, body);
        listChannelMembers(strs[0]);
        return status;
    }

    public String leaveChannel(String... strs) throws IOException, SQLException {
        String url = "/chat/channels/%s/members/me";
        url = String.format(url, strs[0]);
        String status =  this.util.deleteRequest(url, this.token);
        if (!status.equals("204")) {
            System.out.println("Request to leave the channel failed.");
            return "FAIL";
        }
        // Clear channelId members
        DbHelper<ChannelsMembership> channelsMembershipDbHelper = null;
        try {
            channelsMembershipDbHelper = DbHelper.getConnection();
            channelsMembershipDbHelper.delete(ChannelsMembership.class, "ChannelId", strs[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            channelsMembershipDbHelper.closeConnection();
        }
        // Retrieve new members
        listChannelMembers(strs[0]);

        return status;
    }

    public String removeMember(String... strs) throws IOException, SQLException {
        String url = "/chat/channels/%s/members/%s";
        url = String.format(url, strs[0], strs[1]);
        String status =  this.util.deleteRequest(url, this.token);
        if (!status.equals("204")) {
            System.out.println("Request to leave the channel failed.");
            return "FAIL";
        }
        DbHelper<ChannelsMembership> channelsMembershipDbHelper = null;
        try {
            channelsMembershipDbHelper = DbHelper.getConnection();
            channelsMembershipDbHelper.delete(ChannelsMembership.class, strs[0], "memberId", strs[1]);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            channelsMembershipDbHelper.closeConnection();
        }
        return status;

    }

    public String getChannelIdByName(String name) throws IOException, SQLException {
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
