package Bots;

import Components.ZoomAPI;
import Interface.FetchData;
import Utils.OauthClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class botm3 {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.load(new java.io.FileInputStream("src/settings.properties"));
        String test_channel_id = props.getProperty("test_channel_id");
        String my_channel_id = props.getProperty("my_channel_id");
        String yl_channel_id = props.getProperty("yl_channel_id");
        String yl_member_id = props.getProperty("yl_member_id");
        String del_id = "c1b8c72a-08aa-4e19-bd1d-5fffae8616a6";
        String yl_email = "tjuwangyilin@163.com";

        OauthClient client = new OauthClient();
        client.ngrok();
        client.authorize();
        ZoomAPI zoomAPI = new ZoomAPI(client.getToken(), 1);
        String newMessage = "This is a new message sent in " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        zoomAPI.sendMessage("test", newMessage);
        System.out.println(zoomAPI.listChatHistory("test"));

        FetchData fetchByMessage =  (s,maps) -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Messages contains \"").append(s).append("\" are :\n");
            for (Map<String, String> map : maps) {
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
