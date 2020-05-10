package Bots;

import Components.ZoomAPI;
import EventSubjects.NewMemberSubject;
import EventSubjects.NewMessageSubject;
import EventSubjects.UpdateMessageSubject;
import Observers.NewMemberObserver;
import Observers.NewMessageObserver;
import Observers.UpdateMessageObserver;
import Utils.OauthClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class botm4 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Properties props = new Properties();
        props.load(new java.io.FileInputStream("src/settings.properties"));
        OauthClient client = new OauthClient();
        client.authorize();
        ZoomAPI zoomAPI = new ZoomAPI(client.getToken(), 0.5);

        String channelName = "mytest";

        NewMessageSubject newMessageSubject = new NewMessageSubject(zoomAPI, channelName);
        UpdateMessageSubject updateMessageSubject = new UpdateMessageSubject(zoomAPI, channelName);
        NewMemberSubject newMemberSubject = new NewMemberSubject(zoomAPI, channelName);

        NewMessageObserver newMessageObserver = new NewMessageObserver();
        UpdateMessageObserver updateMessageObserver = new UpdateMessageObserver();
        NewMemberObserver newMemberObserver = new NewMemberObserver();

        newMessageSubject.addObserver(newMessageObserver);
        updateMessageSubject.addObserver(updateMessageObserver);
        newMemberSubject.addObserver(newMemberObserver);

        Thread t1 = new Thread(() -> {
            while (true){
                try {
                    List<Map<String, String>> historyCurrent = zoomAPI.listChatHistory(newMessageSubject.getChannelName());
                    newMessageSubject.detect(historyCurrent);
                    newMessageSubject.setHistory(historyCurrent);
                    Thread.sleep(10000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            while (true) {
                try {
                    List<Map<String, String>> historyCurrent = zoomAPI.listChatHistory(updateMessageSubject.getChannelName());
                    updateMessageSubject.detect(historyCurrent);
                    updateMessageSubject.setHistory(historyCurrent);
                    Thread.sleep(10000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();

        Thread t3 = new Thread(() -> {
            while (true) {
                try {
                    List<Map<String, String>> current = zoomAPI.getChannelMembers(newMemberSubject.getChannelName());
                    newMemberSubject.detect(current);
                    newMemberSubject.setMembers(current);
                    Thread.sleep(10000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t3.start();
    }
}
