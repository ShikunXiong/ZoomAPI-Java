package Bots;

import Components.ChatChannels;
import Components.ChatMessages;
import Components.ZoomAPI;
import Utils.AccessLimitService;
import Utils.OauthClient;

import java.text.SimpleDateFormat;
import java.util.*;

public class botm2 {
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
        System.out.println(client.getToken());
        ZoomAPI zoomAPI = new ZoomAPI(client.getToken(), 1);    // calls per second
        String s = "";
        boolean stop = false;

//        // get channel list pass
//        s = zoomAPI.getChatChannels().listChannels();
//        // get a channel pass
//         s = zoomAPI.getChatChannels().getChannel(my_channel_id);
//        // create a channel pass
//        s = zoomAPI.getChatChannels().createChannel("new2", "1", "aa@gmail.com");
//        // update a channel pass
//        s = zoomAPI.getChatChannels().updateChannel(my_channel_id, "my_ttest");
//        // delete a channel pass
//        s = zoomAPI.getChatChannels().deleteChannel(del_id);
//        // list channel members pass
//        s = zoomAPI.getChatChannels().listChannelMembers(test_channel_id);
//        // invite pass
//        s = zoomAPI.getChatChannels().inviteMembers(my_channel_id, yl_email);
//        // join channel pass
//        s = zoomAPI.getChatChannels().joinChannel(yl_channel_id);
//        // leave a channel
//        s = zoomAPI.getChatChannels().leaveChannel(yl_channel_id);
//        // remove a memeber pass
//        s = zoomAPI.getChatChannels().removeMember(my_channel_id, yl_member_id);
//        System.out.println(s);
//        // List all chat messages
//        zoomAPI.getChatMessages().listUserChatMessage(yl_channel_id);
//        // Send a chat message
//        zoomAPI.getChatMessages().sendChatMessage(yl_channel_id);
//        // Update a chat message
//        zoomAPI.getChatMessages().updateChatMessage(yl_channel_id);
//        // delete a chat message
//        zoomAPI.getChatMessages().deleteChatMessage(yl_channel_id);


//        // list
//        Map<String, String> queryMap = new HashMap<>();
//        queryMap.put("to_channel", yl_channel_id);
//        zoomAPI.getChatMessages().listUserChatMessage(queryMap);
//        // put
//        Map<String, String> bodyMap = new HashMap<>();
//        String newMessage = "This is a new message sent in " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        bodyMap.put("message", newMessage);
//        bodyMap.put("to_channel", yl_channel_id);
//        zoomAPI.getChatMessages().sendChatMessage(bodyMap);
//        // Update
//        String messageID = "470d3d68-0afd-40a8-8f17-3509169dc8b0";
//        String updateMessage = "This is an updated message sent in " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        Map<String, String> bodyMap = new HashMap<>();
//        bodyMap.put("message", updateMessage);
//        bodyMap.put("to_channel", yl_channel_id);
//        zoomAPI.getChatMessages().updateChatMessage(messageID, bodyMap);
//        // Delete
//        String messageID = "470d3d68-0afd-40a8-8f17-3509169dc8b0";
//        Map<String, String> queryMap = new HashMap<>();
//        queryMap.put("to_channel", yl_channel_id);
//        zoomAPI.getChatMessages().deleteChatMessage(messageID, queryMap);
    }


    //        while (true) {
//            System.out.println("Choose a function");
//            Scanner sc = new Scanner(System.in);
//            int option = 0;
//            if (sc.hasNextLine()) {
//                option = sc.nextInt();
//            }
//            if (option == 9) {
//                System.exit(1);
//            } else {
//                ChatChannels chatChannels = zoomAPI.getChatChannels();
//                ChatMessages chatMessages = zoomAPI.getChatMessages();
//                if (chatChannels != null && chatMessages != null) {
//                    switch (option) {
//                        case 1:
//                            chatMessages.listUserChatMessage(yl_channel_id);
//                            break;
//                        case 2:
//                            chatMessages.sendChatMessage(yl_channel_id);
//                        default:
//                            break;
//                    }
//                } else {
//                    System.out.println("wait for it");
//                }
//            }
//        }




}
