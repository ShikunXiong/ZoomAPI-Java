package Bots;

import Components.ZoomAPI;
import Utils.OauthClient;
import Interface.FetchData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Properties;

public class botm4 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Properties props = new Properties();
        props.load(new java.io.FileInputStream("src/settings.properties"));
        OauthClient client = new OauthClient();
        client.authorize();
        ZoomAPI zoomAPI = new ZoomAPI(client.getToken(), 1);

        String channelID = zoomAPI.getChannelIdByName("mytest");
        String history = zoomAPI.listChatHistory(channelID);
        int a = 1;
    }
}
