package Observers;

import Interface.myObserver;

import java.util.Map;

public class NewMessageObserver implements myObserver {
    @Override
    public void update(Map<String, String> map) {
        System.out.println("[New message] id=" + map.get("id") +
                " message="+ map.get("message"));
    }
}
