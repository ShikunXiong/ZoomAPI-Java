package Observers;

import Interface.myObserver;

import java.util.Map;

public class UpdateMessageObserver implements myObserver {
    @Override
    public void update(Map<String, String> map) {
        System.out.println("[Update message] id=" + map.get("id") +
                " message="+ map.get("message"));
    }
}
