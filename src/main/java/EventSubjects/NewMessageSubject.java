package EventSubjects;

import Components.ZoomAPI;
import Interface.Subject;
import Interface.myObserver;

import java.io.IOException;
import java.util.*;

public class NewMessageSubject implements Subject {
    private String channelName;
    private List<Map<String, String>> history;
    private List<myObserver> observers= new ArrayList<>();

    public String getChannelName() {
        return channelName;
    }

    public List<Map<String, String>> getHistory() {
        return history;
    }

    public void setHistory(List<Map<String, String>> history) {
        this.history = history;
    }

    public NewMessageSubject(ZoomAPI zoomAPI, String channelName) throws IOException, InterruptedException {
        this.channelName = channelName;
        this.history = new ArrayList<>();
        this.history = zoomAPI.listChatHistory(this.channelName);
    }

    public void detect(List<Map<String, String>> historyCurrent){
        if (historyCurrent.size()<1 && this.history.size()<1){
            System.out.println("No new message");
        }else if(history.size()<1){
            for (Map<String, String> map : historyCurrent) {
                notifyObserver(map);
            }
        }else {
            for (int i = 0; i < historyCurrent.size(); i++) {
                if (historyCurrent.get(i).get("id").equals(history.get(0).get("id"))) {
                    if (i==0) {
                        System.out.println("[New Message] No new message");
                    }
                    break;
                }
                Map<String, String> m = new HashMap<>();
                m.put("id", historyCurrent.get(i).get("id"));
                m.put("message", historyCurrent.get(i).get("message"));
                notifyObserver(m);
            }
        }
    }

    @Override
    public void addObserver(myObserver obj) {
        observers.add(obj);
    }

    @Override
    public void deleteObserver(myObserver obj) {
        int i = observers.indexOf(obj);
        if(i>=0){
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObserver(Map<String, String> map) {
        for(int i=0;i<observers.size();i++){
            myObserver o = observers.get(i);
            o.update(map);
        }
    }

}
