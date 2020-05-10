package EventSubjects;

import Components.ZoomAPI;
import Interface.Subject;
import Interface.myObserver;

import java.io.IOException;
import java.util.*;

public class NewMemberSubject implements Subject {
    public String getChannelName() {
        return channelName;
    }

    private String channelName;

    public void setMembers(List<Map<String, String>> members) {
        this.members = members;
    }

    private List<Map<String, String>> members;
    private List<myObserver> observers= new ArrayList<>();
    private Set ids;

    public NewMemberSubject(ZoomAPI zoomAPI, String channelName) throws IOException, InterruptedException {
        this.channelName = channelName;
        members = new ArrayList<>();
        members = zoomAPI.getChannelMembers(channelName);
        ids = new HashSet();
        for (int i=0; i<members.size(); i++){
            ids.add(members.get(i).get("id"));
        }
    }

    public void detect(List<Map<String, String>> current) {
        boolean flag = false;
        for (int i=0; i<current.size(); i++){
            if (!ids.contains(current.get(i).get("id"))){
                ids.add(current.get(i).get("id"));
                flag = true;
                notifyObserver(current.get(i));
            }
        }
        if (!flag){
            System.out.println("[New member] No new member added");
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
