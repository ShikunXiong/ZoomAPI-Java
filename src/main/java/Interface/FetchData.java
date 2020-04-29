package Interface;

import java.util.Map;

@FunctionalInterface
public interface FetchData {
    String fetchBy(String s, Map<String, String>[] maps);
}
