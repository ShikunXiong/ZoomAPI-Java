package Components;

import Utils.HttpUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class report {
    String token;
    HttpUtils util;

    public report(String token){
        this.token = token;
        util = new HttpUtils();
    }

    public String getAccountReport(String... strs) throws IOException {
        String url = "/report/users?from=%s&to=%s";
        DateFormat df = new SimpleDateFormat("yyyy/mm/dd");
        String start_date = df.format(strs[0]);
        String end_date = df.format(strs[1]);
        String.format(url, start_date, end_date);
        return util.getRequest(url, this.token);
    }

    public String getUserReport(String... strs) throws IOException {
        String url = "/report/users/%s/meetings?from=%s&to=%s";
        DateFormat df = new SimpleDateFormat("yyyy/mm/dd");
        String start_date = df.format(strs[1]);
        String end_date = df.format(strs[2]);
        String.format(url, strs[0], start_date, end_date);
        return util.getRequest(url, this.token);
    }

}
