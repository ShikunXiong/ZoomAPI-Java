package Bots;

import Components.ZoomAPI;
import Utils.HttpUtils;
import Utils.OauthClient;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import okhttp3.*;
import okhttp3.internal.http2.Header;
import org.apache.commons.logging.Log;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class botm2 {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.load(new java.io.FileInputStream("src/settings.properties"));
        String test_channel_id = props.getProperty("test_channel_id");
        String my_channel_id = props.getProperty("my_channel_id");
        String yl_channel_id = props.getProperty("yl_channel_id");
        String yl_member_id = props.getProperty("yl_member_id");
        String del_id = "c1b8c72a-08aa-4e19-bd1d-5fffae8616a6";
        String yl_email = "tjuwangyilin@163.com";

        OauthClient client = new OauthClient();
        client.ApplyToken();
        System.out.println(client.getToken());
        ZoomAPI zoomAPI = new ZoomAPI(client.getToken());
        String s = "";
//        // get channel list pass
//        s = zoomAPI.getChatChannels().listChannels();
//        // get a channel pass
//         s = zoomAPI.getChatChannels().getChannel(my_channel_id);
//        // create a channel pass
//        s = zoomAPI.getChatChannels().createChannel("new2", "1", "aa@gmail.com");
//        // update a channel pass
//        s = zoomAPI.getChatChannels().updateChannel(my_channel_id, "my_ttest");
//        // delete a channel pass
//        s = zoomAPI.getChatChannels().deleteChannel(del_id);
//        // list channel members pass
//        s = zoomAPI.getChatChannels().listChannelMembers(test_channel_id);
//        // invite pass
//        s = zoomAPI.getChatChannels().inviteMembers(my_channel_id, yl_email);
//        // join channel pass
//        s = zoomAPI.getChatChannels().joinChannel(yl_channel_id);
//        // leave a channel
//        s = zoomAPI.getChatChannels().leaveChannel(yl_channel_id);
//        // remove a memeber pass
//        s = zoomAPI.getChatChannels().removeMember(my_channel_id, yl_member_id);
//        System.out.println(s);
//        zoomAPI.getChatMessages().listUserChatMessage(yl_channel_id);
//        zoomAPI.getChatMessages().sendChatMessage(yl_channel_id);
//        zoomAPI.getChatMessages().updateChatMessage(yl_channel_id);
//        zoomAPI.getChatMessages().deleteChatMessage(yl_channel_id);
    }

}
