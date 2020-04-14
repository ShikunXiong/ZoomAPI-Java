package Utils;

import okhttp3.*;

import java.io.IOException;
import java.util.Base64;

public class HttpUtils {
    public static final String BASE_URL = "https://api.zoom.us/v2";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public HttpUtils(){}
    public String get_request(String url, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String valid_token = "Bearer " + token;
        Request request = new Request.Builder()
                .addHeader("authorization", valid_token)
                .addHeader("Content-type", "application/json")
                .url(BASE_URL + url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String post_request(String url, RequestBody body) throws IOException {
            OkHttpClient client = new OkHttpClient();
            Base64.Encoder encoder = Base64.getEncoder();
            String s = "QMXaCuYT8iuhYPJokx4lw:k3BKaBQUubz3fFihp80JzZ7eg5vKyGno";
            s = encoder.encodeToString(s.getBytes());
            String auth = "Basic " + s;
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", auth)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }

}


