package Components;

import Interface.FetchData;
import Utils.AccessLimitService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.time.temporal.ChronoUnit;

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

    public String sendMessage(String channelName, String message) throws IOException, InterruptedException, SQLException {
        String channelID = getChannelIdByName(channelName);
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("message", message);
        bodyMap.put("to_channel", channelID);
        return this.getChatMessages().sendChatMessage(bodyMap);
    }

    public String listChatHistory(String channelName) throws IOException, InterruptedException, SQLException {
        String channelID = getChannelIdByName(channelName);
        StringBuilder res = new StringBuilder();
        String nextPageToken = "";
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("to_channel", channelID);
        do {
            Map<String, String>[] maps = this.getChatMessages().listUserChatMessageAll(queryMap);
            nextPageToken = maps[0].get("next_page_token");
            for (int i = 1; i < maps.length; i++) {
                res.append(maps[i].get("message")).append('\n');
            }
            queryMap.put("next_page_token", nextPageToken);
        } while (nextPageToken.length() != 0);
        return res.toString();
    }

    public String listChatHistory(String channelName, String startDate, String endDate) throws IOException, InterruptedException, SQLException {
        StringBuilder res = new StringBuilder();
        String channelID = getChannelIdByName(channelName);
        LocalDate s = LocalDate.parse(startDate), e = LocalDate.parse(endDate);
        long daysBetween = ChronoUnit.DAYS.between(s, e);
        e = daysBetween > 5 ? s.plusDays(5) : e;
        for (; !s.equals(e.plusDays(1)); s = s.plusDays(1)) {
            res.append("Message from ").append(s.toString()).append(" are ").append('\n');
            String nextPageToken = "";
            Map<String, String> queryMap = new HashMap<>();
            queryMap.put("to_channel", channelID);
            queryMap.put("date", s.toString());
            do {
                Map<String, String>[] maps = this.getChatMessages().listUserChatMessageAll(queryMap);
                nextPageToken = maps[0].get("next_page_token");
                for (int i = 1; i < maps.length; i++) {
                    res.append(maps[i].get("message")).append('\n');
                }
                queryMap.put("next_page_token", nextPageToken);
            } while (nextPageToken.length() != 0);
        }
        return res.toString();
    }

    public String search(String channelName, String keyWord, FetchData func) throws IOException, InterruptedException, SQLException {
        this.getAccessLimitService().acquire();
        ChatMessages ChatMessage = this.getChatMessages();
        String channelID = getChannelIdByName(channelName);
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("to_channel", channelID);
        Map<String, String>[] maps = ChatMessage.listUserChatMessageAll(queryMap);
        return func.fetchBy(keyWord, maps);
    }
}

