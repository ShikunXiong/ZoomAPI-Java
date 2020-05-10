package Interface;

import java.util.Map;

public interface Subject {
    void addObserver(myObserver obj);
    void deleteObserver(myObserver obj);
    void notifyObserver(Map<String, String> map);
}
