package Components;

import Utils.HttpUtils;
import okhttp3.RequestBody;

import java.io.IOException;

public class meeting {
    String token;
    HttpUtils util;

    public meeting(String token){
        this.token = token;
        util = new HttpUtils();
    }

    public String list(String... strs) throws IOException {
        String url = "/users/%s/meetings";
        url = String.format(url, strs);
        return util.getRequest(url, this.token);
    }

    public String create(String... strs) throws IOException {
        String url = "/users/%s/meetings";
        url = String.format(url, strs);
        RequestBody body = RequestBody.create(null, "");
        return util.postRequest(url, this.token, body);
    }

    public String get(String... strs) throws IOException {
        String url = "/meetings/%s";
        url = String.format(url, strs);
        return util.getRequest(url, this.token);
    }

    public String update(String... strs) throws IOException {
        String url = "/meetings/%s";
        url = String.format(url, strs);
        RequestBody body = RequestBody.create(null, "");
        return util.patchRequest(url, this.token, body);
    }

    public String delete(String... strs) throws IOException {
        String url = "/meetings/%s";
        url = String.format(url, strs);
        return util.deleteRequest(url, this.token);
    }
}
