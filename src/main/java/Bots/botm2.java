package Bots;

import Utils.HttpUtils;
import Utils.OAuthClient;

import java.io.IOException;

public class botm2 {
    public static void main(String[] args) throws IOException {
        OAuthClient client = new OAuthClient();
        client.client_authorize();
        HttpUtils util = new HttpUtils(client);
        String s = util.get_request("/chat/users/me/channels");
        int i = 21;
    }
}
