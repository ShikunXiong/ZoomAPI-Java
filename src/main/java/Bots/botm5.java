package Bots;

import Components.ZoomAPI;
import Utils.OauthClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class botm5 {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.load(new java.io.FileInputStream("src/settings.properties"));

        OauthClient client = new OauthClient();
        client.authorize();

        ZoomAPI zoomAPI = new ZoomAPI(client.getToken(), 0.2);
//        String s = zoomAPI.getChatChannels().listChannels();
//        String s = zoomAPI.getChatChannels().createChannel("Happy hiking", "1", "tjuwangyilin@163.com");
//        String s2 = zoomAPI.getChatChannels().deleteChannel("d9ffb162-47e1-438c-b473-bdf31b36a2b1");
        int i = 0;
//        System.out.println(zoomAPI.listChatHistory("test"));
//        String newMessage = "This is a new message sent in " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        Map<String, String> bodyMap2 = new HashMap<>();
//        bodyMap2.put("message", newMessage);
//        bodyMap2.put("to_channel", "c1e85cd0e22844baaaa9cb2bf55f7704");
//        zoomAPI.getChatMessages().sendChatMessage(bodyMap2);
//        System.out.println(zoomAPI.listChatHistory("test"));
//        // Update a message
//        String messageId = "3743feb0-dc2f-4e38-b81f-a47d8b3f38a4";
//        Map<String, String> bodyMap = new HashMap<>();
//        bodyMap.put("message", "This is an updated message sent in " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        bodyMap.put("to_channel", "c1e85cd0e22844baaaa9cb2bf55f7704");
//        zoomAPI.getChatMessages().updateChatMessage(messageId, bodyMap);
//
//        // delete a message
//        String messageId2 = "2d4a7ed4-a686-4cfa-bec5-ac19805a3959";
//        Map<String, String> queryMap = new HashMap<>();
//        queryMap.put("to_channel", "c1e85cd0e22844baaaa9cb2bf55f7704");
//        zoomAPI.getChatMessages().deleteChatMessage(messageId2, queryMap);

    }
}
