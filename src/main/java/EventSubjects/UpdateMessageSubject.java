package EventSubjects;

import Components.ZoomAPI;
import Interface.Subject;
import Interface.myObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateMessageSubject implements Subject {
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

    public UpdateMessageSubject(ZoomAPI zoomAPI, String channelName) throws IOException, InterruptedException {
        this.channelName = channelName;
        this.history = new ArrayList<>();
        this.history = zoomAPI.listChatHistory(this.channelName);
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
                        notifyObserver(historyCurrent.get(i));
                    }
                    cur = j+1;
                    break;
                }
            }
        }
        if (!flag){
            System.out.println("[Update Message] No message update");
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
