package Components;
import Utils.HttpUtils;
import okhttp3.RequestBody;

import java.io.IOException;

public class recording {
    String token;
    HttpUtils util;

    public recording(String token){
        this.token = token;
        util = new HttpUtils();
    }

    public String list(String... strs) throws IOException {
        String url = "/users/%s/recordings";
        url = String.format(url, strs);
        return util.getRequest(url, this.token);
    }

    public String get(String... strs) throws IOException {
        String url = "/meetings/%s/recordings";
        url = String.format(url, strs);
        return util.getRequest(url, this.token);
    }

    public String delete(String... strs) throws IOException {
        String url = "/meetings/%s/recordings";
        url = String.format(url, strs);
        return util.deleteRequest(url, this.token);
    }
}
