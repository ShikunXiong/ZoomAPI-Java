package Bots;

import Components.ChatChannels;
import Components.ChatMessages;
import Components.ZoomAPI;
import Interface.FetchData;
import Utils.AccessLimitService;
import Utils.OauthClient;

import java.text.SimpleDateFormat;
import java.util.*;

public class botm3 {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.load(new java.io.FileInputStream("src/settings.properties"));

        OauthClient client = new OauthClient();
        client.authorize();

        ZoomAPI zoomAPI = new ZoomAPI(client.getToken(), 1);

        //Below is the test
        String newMessage = "This is a new message sent in " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        zoomAPI.sendMessage("test", newMessage);
        System.out.println(zoomAPI.listChatHistory("test", "2020-04-24", "2020-04-29"));

        FetchData fetchByMessage =  (s, maps) -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Messages contains \"").append(s).append("\" are :\n");
            for (Map<String, String> map : maps) {
                if (!map.containsKey("message")){
                    continue;
                }
                if (map.get("message").contains(s)) {
                    sb.append(map.get("message")).append(' ');
                }
            }
            return sb.toString();
        };

        FetchData fetchBySender =  (s,maps) -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Messages sent by \"").append(s).append("\" are :\n");
            for (Map<String, String> map : maps) {
                if (!map.containsKey("sender")){
                    continue;
                }
                if (map.get("sender").contains(s)) {
                    sb.append(map.get("message")).append(' ');
                }
            }
            return sb.toString();
        };

        System.out.println(zoomAPI.search("test", "sent", fetchByMessage));
        // Thread.sleep(1000);
        System.out.println(zoomAPI.search("test", "wangyilin", fetchBySender));

    }
}
