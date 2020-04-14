package Bots;

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
import java.util.Scanner;

public class test2{
    private static String getRedirectUrl(String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(path)
                .openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(5000);
        return conn.getHeaderField("Location");
    }


    public static void main(String[] args) throws Exception {
        String browser_path = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
        String url = "https://zoom.us/oauth/authorize?response_type=code&client_id=QMXaCuYT8iuhYPJokx4lw&redirect_uri=https%3A%2F%2Fzoom.us";
        List<String> cmd = new ArrayList<String>();
        cmd.add(browser_path);
        cmd.add(url);
        ProcessBuilder process = new ProcessBuilder(cmd);
        process.start();
        System.out.println("Input the code: ");
        String code = "";
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            code = scanner.nextLine();
        }
        scanner.close();
        OAuthClientRequest ClientRequest = OAuthClientRequest
                .tokenLocation("https://zoom.us/oauth/token")
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId("QMXaCuYT8iuhYPJokx4lw")
                .setClientSecret("k3BKaBQUubz3fFihp80JzZ7eg5vKyGno")
                .setRedirectURI("https://zoom.us")
                .setCode(code)
                .buildQueryMessage();
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(ClientRequest, "POST");

        String accessToken = oAuthResponse.getAccessToken();
        Long expiresIn = oAuthResponse.getExpiresIn();
        int k = 1;
    }

}
