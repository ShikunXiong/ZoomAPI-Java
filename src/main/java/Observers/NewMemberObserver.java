package Observers;

import Interface.myObserver;

import java.util.Map;

public class NewMemberObserver implements myObserver {
    @Override
    public void update(Map<String, String> map) {
        System.out.println("[New member] " + map.get("name") + " added to this channel");
    }
}
