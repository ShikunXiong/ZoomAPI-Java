package Utils;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.util.*;

public class OauthClient implements Auth {
    String token = "";
    Credential credential;

    public String getToken() {
        return this.credential.getAccessToken();
    }

    @Override
    public void authorize() throws IOException {
        final String SCOPE = "read";
        final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();
        Properties props = new Properties();
        props.load(new java.io.FileInputStream("src/settings.properties"));
        final String TOKEN_SERVER_URL =props.getProperty("authorize_url") ;
        final String AUTHORIZATION_SERVER_URL = props.getProperty("token_url");
        final String client_id = props.getProperty("client_id");
        final String client_secret = props.getProperty("client_secret");
        final String redirect_url = props.getProperty("redirect_url");
        final int port = Integer.parseInt(props.getProperty("port"));
        AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken
                .authorizationHeaderAccessMethod(),
                HTTP_TRANSPORT,
                JSON_FACTORY,
                new GenericUrl(TOKEN_SERVER_URL),
                new ClientParametersAuthentication(
                        client_id, client_secret),
                client_id,
                AUTHORIZATION_SERVER_URL).setScopes(Arrays.asList(SCOPE))
                .build();
            // authorize
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost(
                    redirect_url).setPort(port).build();
            credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("me");
        }
    }



