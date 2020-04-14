package Bots;

import Utils.HttpUtils;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class test {

    String browser_path = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
    public static String url = "https://zoom.us/oauth/authorize?response_type=code&client_id=QMXaCuYT8iuhYPJokx4lw&redirect_uri=https://zoom.us";
    public static void main(String[] args) throws IOException {
//        test t = new test();
//        List<String> cmd = new ArrayList<String>();
//        cmd.add(t.browser_path);
//        cmd.add(t.url);
//        ProcessBuilder process = new ProcessBuilder(cmd);
//        process.start();


        RequestBody body = new FormBody.Builder()
                .add("grant_type","authorization_code")
                .add("code", "5rV8Fz5VUt_N5slpjrPQW2rBVw2Z3Jymw")
                .add("redirect_uri", "https://zoom.us")
                .build();
        HttpUtils util = new HttpUtils();
        String s = util.post_request("https://api.zoom.us/oauth/token", body);
        JSONObject jsonObject = JSONObject.parseObject(s);
        s = jsonObject.getString("refresh_token");
        int i = 1;
    }}
