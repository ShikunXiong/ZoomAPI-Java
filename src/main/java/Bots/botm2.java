package Bots;

import Components.ZoomAPI;
import Utils.AccessLimitService;
import Utils.OauthClient;
import java.util.Properties;
import java.util.Scanner;

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
        client.ApplyToken();
        System.out.println(client.getToken());
        ZoomAPI zoomAPI = new ZoomAPI(client.getToken());
        String s = "";
        boolean stop = false;
        AccessLimitService accessLimitService = new AccessLimitService(0.1);
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
        while (!stop) {
            System.out.println("Choose a function");
            Scanner sc = new Scanner(System.in);
            int option = 0;
            if (sc.hasNextLine()) {
                option = sc.nextInt();
            }
            if (option == 9) {
                stop = true;
            } else {
                if (accessLimitService.tryAcquire()) {
                    switch (option) {
                        case 1:
                            zoomAPI.getChatMessages().listUserChatMessage(yl_channel_id);
                            break;
                        case 2:
                            zoomAPI.getChatMessages().sendChatMessage(yl_channel_id);
                        default:
                            break;
                    }
                } else {
                    System.out.println("wait for it");
                }
            }
        }
    }

}
