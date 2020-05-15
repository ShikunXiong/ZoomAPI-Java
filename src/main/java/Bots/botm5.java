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
//        String s = zoomAPI.getChatChannels().createChannel("Happy hiking", "1", "tjuwangyilin@163.com", "yilin.wang@163.com");
//        String s2 = zoomAPI.getChatChannels().deleteChannel("d9ffb162-47e1-438c-b473-bdf31b36a2b1");
//         String s3 = zoomAPI.getChatChannels().listChannelMembers("f0583de6-4a8f-4bbb-ac2b-fc62385f833a");
//        int i = 0;
//        System.out.println(zoomAPI.listChatHistory("test"));
//        String newMessage = "This is a new message sent in " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        Map<String, String> bodyMap2 = new HashMap<>();
//        bodyMap2.put("message", newMessage);
//        bodyMap2.put("to_channel", "c1e85cd0e22844baaaa9cb2bf55f7704");
//        zoomAPI.getChatMessages().sendChatMessage(bodyMap2);
//        System.out.println(zoomAPI.listChatHistory("test"));
//        zoomAPI.getChatChannels().inviteMembers()
        // Update a message
//        String messageId = "8fcc447c-e0d0-4e0a-bbe0-bf2f0b2bfe4b";
//        Map<String, String> bodyMap = new HashMap<>();
//        bodyMap.put("message", "This is an updated message sent in " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        bodyMap.put("to_channel", "c1e85cd0e22844baaaa9cb2bf55f7704");
//        zoomAPI.getChatMessages().updateChatMessage(messageId, bodyMap);
//
        // delete a message
//        String messageId2 = "bd14c5aa-b905-4e31-a51f-07c7dfab1191";
//        Map<String, String> queryMap = new HashMap<>();
//        queryMap.put("to_channel", "c1e85cd0e22844baaaa9cb2bf55f7704");
//        zoomAPI.getChatMessages().deleteChatMessage(messageId2, queryMap);

    }
}
