package Components;

import Interface.FetchData;
import Utils.AccessLimitService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ZoomAPI {
    private final ChatChannels chatChannels;
    private final ChatMessages chatMessages;
    private final AccessLimitService accessLimitService;

    public ZoomAPI(String token, double limit) {
        this.chatChannels = new ChatChannels(token);
        this.chatMessages = new ChatMessages(token);
        this.accessLimitService = new AccessLimitService(limit);
    }

    public ChatChannels getChatChannels() throws InterruptedException {
        this.getAccessLimitService().acquire();
        System.out.println("Got Permit!");
        return this.chatChannels;
    }

    public ChatMessages getChatMessages() throws InterruptedException {
        this.getAccessLimitService().acquire();
        System.out.println("Got Permit!");
        return this.chatMessages;
    }

    public AccessLimitService getAccessLimitService() {
        return accessLimitService;
    }

    public String getChannelIdByName(String name) throws IOException, InterruptedException {
        String result = "";
        String response = this.getChatChannels().listChannels();
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

    public String sendMessage(String channelName, String message) throws IOException, InterruptedException {
        String channelID = getChannelIdByName(channelName);
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("message", message);
        bodyMap.put("to_channel", channelID);
        return this.getChatMessages().sendChatMessage(bodyMap);
    }

    public String listChatHistory(String channelName) throws IOException, InterruptedException {
        String channelID = getChannelIdByName(channelName);
        StringBuilder res = new StringBuilder();
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("to_channel", channelID);
        Map<String, String>[] maps = this.getChatMessages().listUserChatMessageAll(queryMap);
        String nextPageToken = maps[0].get("next_page_token");
        for (int i = 1; i < maps.length; i++) {
            res.append(maps[i].get("message"));
        }
        while (nextPageToken != null) {
            queryMap.put("next_page_token", nextPageToken);
            maps = this.getChatMessages().listUserChatMessageAll(queryMap);
            nextPageToken = maps[0].get("next_page_token");
            for (int i = 1; i < maps.length; i++) {
                res.append(maps[i].get("message"));
            }
        }
        return res.toString();
    }

    public String search(String channelName, String keyWord, FetchData func) throws IOException, InterruptedException {
        this.getAccessLimitService().acquire();
        ChatMessages ChatMessage = this.getChatMessages();
        String channelID = getChannelIdByName(channelName);
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("to_channel", channelID);
        Map<String, String>[] maps = ChatMessage.listUserChatMessageAll(queryMap);
        return func.fetchBy(keyWord, maps);
    }
}

