package Bots;

import Components.ZoomAPI;
import Utils.OauthClient;



import java.io.IOException;
import java.util.*;
import java.util.Properties;

public class botm4 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Properties props = new Properties();
        props.load(new java.io.FileInputStream("src/settings.properties"));
        OauthClient client = new OauthClient();
        client.authorize();
        ZoomAPI zoomAPI = new ZoomAPI(client.getToken(), 0.5);
        NewMessage newMessage = new NewMessage(zoomAPI, "mytest");
        UpdateMessage updateMessage = new UpdateMessage(zoomAPI, "mytest");
        NewMember newMember = new NewMember(zoomAPI, "mytest");
    }
}

abstract class ThreadObject extends Thread {

    public boolean stopMe;
    public ZoomAPI zoomAPI;

    public ThreadObject(ZoomAPI zoomAPI) {
        super();
        stopMe = false;
        this.zoomAPI = zoomAPI;
        this.start();
    }

    public abstract void run();
}

class NewMessage extends ThreadObject{
    private String channelName;
    private List<Map<String, String>> history;

    public NewMessage(ZoomAPI zoomAPI, String channelName) throws IOException, InterruptedException {
        super(zoomAPI);
        this.channelName = channelName;
        history = new ArrayList<>();
        history = zoomAPI.listChatHistory(channelName);
    }

    @Override
    public void run() {
        while (!this.stopMe){
        try {
            List<Map<String, String>> historyCurrent = zoomAPI.listChatHistory(channelName);
            detect(historyCurrent);
            history = historyCurrent;
            Thread.sleep(10000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }}

    public void detect(List<Map<String, String>> historyCurrent){
        if (historyCurrent.size()<1 && this.history.size()<1){
            System.out.println("No new message");
        }else if(history.size()<1){
            for (Map<String, String> map : historyCurrent) {
                System.out.println(map.get("id") + " : " + map.get("message"));
            }
        }else {
            for (int i = 0; i < historyCurrent.size(); i++) {
                if (historyCurrent.get(i).get("id").equals(history.get(0).get("id"))) {
                    if (i==0) {
                        System.out.println("[New Message] No new message");
                    }
                    break;
                }
                System.out.println("[New Message]" + historyCurrent.get(i).get("id") + " : " + historyCurrent.get(i).get("message"));
            }
        }
    }
}

class UpdateMessage extends ThreadObject {
    private String channelName;
    private List<Map<String, String>> history;

    public UpdateMessage(ZoomAPI zoomAPI, String channelName) throws IOException, InterruptedException {
        super(zoomAPI);
        this.channelName = channelName;
        history = new ArrayList<>();
        history = zoomAPI.listChatHistory(channelName);
    }

    @Override
    public void run() {
        while (!this.stopMe) {
            try {
                List<Map<String, String>> historyCurrent = zoomAPI.listChatHistory(channelName);
                detect(historyCurrent);
                history = historyCurrent;
                Thread.sleep(10000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void detect(List<Map<String, String>> historyCurrent) {
        int cur = 0;
        boolean flag = false;
        for (int i = 0; i < history.size(); i++){
            String id = history.get(i).get("id");
            String message = history.get(i).get("message");
            for (int j = cur; j < historyCurrent.size(); j++) {
                if (id.equals(historyCurrent.get(j).get("id"))){
                    if (!message.equals(historyCurrent.get(j).get("message"))){
                        flag = true;
                        System.out.println("[Message Update] " + historyCurrent.get(j).get("id") + ":" + historyCurrent.get(j).get("message"));
                    }
                    cur = j+1;
                    break;
                }
            }
        }
        if (!flag){
            System.out.println("[Message Update] No message update");
        }
    }
}

class NewMember extends ThreadObject {
    private String channelName;
    private List<Map<String, String>> members;
    private Set ids;

    public NewMember(ZoomAPI zoomAPI, String channelName) throws IOException, InterruptedException {
        super(zoomAPI);
        this.channelName = channelName;
        members = new ArrayList<>();
        members = zoomAPI.getChannelMembers(channelName);
        ids = new HashSet();
        for (int i=0; i<members.size(); i++){
            ids.add(members.get(i).get("id"));
        }
    }

        @Override
    public void run() {
        while (!this.stopMe) {
            try {
                List<Map<String, String>> current = zoomAPI.getChannelMembers(channelName);
                detect(current);
                members = current;
                Thread.sleep(10000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void detect(List<Map<String, String>> current) {
        boolean flag = false;
        for (int i=0; i<current.size(); i++){
            if (!ids.contains(current.get(i).get("id"))){
                ids.add(current.get(i).get("id"));
                flag = true;
                System.out.println("[New member] " + current.get(i).get("name") + " added to this channel");
            }
        }
        if (!flag){
            System.out.println("[New member] No new member added");
        }
    }
}

