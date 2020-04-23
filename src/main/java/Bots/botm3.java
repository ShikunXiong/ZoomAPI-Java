package Bots;

import Components.ZoomAPI;
import Utils.OauthClient;

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

        String s = zoomAPI.getChatChannels().getChannelIdByName("new");
        int a = 1;
    }
}
