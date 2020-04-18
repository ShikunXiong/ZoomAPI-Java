package Components;

import Utils.HttpUtils;
import com.google.common.base.Joiner;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.Map;

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

    public String create(String url_fill, Map<String, String> queryMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/users/%s/meetings";
        url = String.format(url, url_fill);
        url += query;
        RequestBody body = RequestBody.create(null, "");
        return util.postRequest(url, this.token, body);
    }

    public String get(String... strs) throws IOException {
        String url = "/meetings/%s";
        url = String.format(url, strs);
        return util.getRequest(url, this.token);
    }

    public String update(String url_fill, Map<String, String> queryMap) throws IOException {
        String query = Joiner.on("&").withKeyValueSeparator("=").join(queryMap);
        String url = "/meetings/%s";
        url = String.format(url, url_fill);
        url += query;
        RequestBody body = RequestBody.create(null, "");
        return util.patchRequest(url, this.token, body);
    }

    public String delete(String... strs) throws IOException {
        String url = "/meetings/%s";
        url = String.format(url, strs);
        return util.deleteRequest(url, this.token);
    }
}
