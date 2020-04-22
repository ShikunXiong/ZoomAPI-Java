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
        while (true) {
            System.out.println("Choose a function: \n" +
                    "1. Test ChatChannels\n" +
                    "2. Test ChatMessages\n" +
                    "3. Quit");
            Scanner sc = new Scanner(System.in);
            int option = 0;
            if (sc.hasNextLine()) {
                option = sc.nextInt();
            }
            if (option < 1 || option > 3) {
                System.out.println("Invalid argument");
                continue;
            }
            if (option == 1) {
                // Chat Channels
                System.out.println("Choose a function: \n" +
                        "1. List user's channels\n" +
                        "2. Create a channel\n" +
                        "3. Get a channel\n" +
                        "4. Update a channel\n" +
                        "5. Delete a channel\n" +
                        "6. List channel members\n" +
                        "7. Invite channel members\n" +
                        "8. Join a channel\n" +
                        "9. Leave a channel\n" +
                        "10. Remove a member\n" +
                        "11. Quit");
                int opt = 0;
                if (sc.hasNextLine()) {
                    opt = sc.nextInt();
                }
                switch (opt) {
                    case 1:
                        System.out.println(zoomAPI.getChatChannels().listChannels());
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    default:
                        break;
                }

            } else if (option == 2){
                System.out.println("Current channels are : ");
                System.out.println(zoomAPI.getChatChannels().listChannels());
                System.out.println("Choose a function: \n" +
                        "1. List channel messages\n" +
                        "2. Send a message to the channel\n" +
                        "3. Update a message in the channel\n" +
                        "4. Delete a message in the channel\n" +
                        "5. Quit");
                int opt = 0;
                if (sc.hasNextLine()) {
                    opt = sc.nextInt();
                }
                String channelID = null;
                switch (opt) {
                    case 1:
                        channelID = getChannelID();
                        Map<String, String> queryMap1 = new HashMap<>();
                        queryMap1.put("to_channel", channelID);
                        zoomAPI.getChatMessages().listUserChatMessage(queryMap1);
                        break;
                    case 2:
                        channelID = getChannelID();
                        String newMessage2 = getNewMessage();
                        Map<String, String> bodyMap = new HashMap<>();
                        bodyMap.put("message", newMessage2);
                        bodyMap.put("to_channel", channelID);
                        zoomAPI.getChatMessages().sendChatMessage(bodyMap);
                        break;
                    case 3:
                        channelID = getChannelID();
                        System.out.println("Here are messages you have right now: ");
                        Map<String, String> queryMap3 = new HashMap<>();
                        queryMap3.put("to_channel", channelID);
                        zoomAPI.getChatMessages().listUserChatMessage(queryMap3);
                        String MessageID3 = getMessageID();
                        String newMessage3 = getNewMessage();
                        Map<String, String> bodyMap3 = new HashMap<>();
                        bodyMap3.put("message", newMessage3);
                        bodyMap3.put("to_channel", channelID);
                        zoomAPI.getChatMessages().updateChatMessage(MessageID3, bodyMap3);
                        break;
                    case 4:
                        channelID = getChannelID();
                        System.out.println("Here are messages you have right now: ");
                        Map<String, String> queryMap4 = new HashMap<>();
                        queryMap4.put("to_channel", channelID);
                        zoomAPI.getChatMessages().listUserChatMessage(queryMap4);
                        String MessageID4 = getMessageID();
                        queryMap4 = new HashMap<>();
                        queryMap4.put("to_channel", channelID);
                        zoomAPI.getChatMessages().deleteChatMessage(MessageID4, queryMap4);
                        break;
                    default:
                        break;
                }
            } else {
                System.exit(1);
            }

        }

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
    private static String getChannelID() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Which channel do you want to test?");
        String channelID = null;
        if (sc.hasNextLine()) {
            channelID = sc.nextLine();
        }
        return channelID;
    }
    private static String getNewMessage() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input your new message: ");
        String newMessage = null;
        if (sc.hasNextLine()) {
            newMessage = sc.nextLine();
        }
        return newMessage;
    }
    private static String getMessageID() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input message id you want to modify: ");
        String messageID = null;
        if (sc.hasNextLine()) {
            messageID = sc.nextLine();
        }
        return messageID;
    }






}
