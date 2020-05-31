package Utils;

import Components.ChatMessages;
import Components.ZoomAPI;
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
import com.google.gson.Gson;
import database.DbHelper;
import models.AccessCredential;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

public class OauthClient implements Auth {
    private String accessToken = "";
    Credential credential;

    public String getToken() {
        return getAccessToken();
    }

    private String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void authorize() throws IOException, SQLException, InterruptedException {
        final String SCOPE = "read";
        final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();
        Properties props = new Properties();
        props.load(new java.io.FileInputStream("src/settings.properties"));
        final String TOKEN_SERVER_URL =props.getProperty("authorize_url") ;
        final String AUTHORIZATION_SERVER_URL = props.getProperty("token_url");
        final String client_id = props.getProperty("yl_client_id");
        final String client_secret = props.getProperty("yl_client_secret");
        final String redirect_url = props.getProperty("redirect_url");
        final int port = Integer.parseInt(props.getProperty("port"));

        if (!inValid(client_id)) {
            // if current token is valid, return.
            System.out.println("--- Stored token is valid! ---");
            Thread.sleep(3000);
            return;
        }
        Thread.sleep(3000);
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
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost(redirect_url).setPort(port).build();
        credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("me");
        String accessToken = credential.getAccessToken();
        System.out.println("--- New token is stored in the database ---");
        setAccessToken(accessToken);
        // Update accessToken
        AccessCredential accessCredential = new AccessCredential(0, client_id, accessToken);
        DbHelper<AccessCredential> accessCredentialDbHelper = null;
        try {
            accessCredentialDbHelper = DbHelper.getConnection();
            accessCredentialDbHelper.update(accessCredential);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            accessCredentialDbHelper.closeConnection();
        }
    }

        private boolean inValid(String clientId) throws SQLException {
            System.out.println("--- Checking stored token ---");
            DbHelper<AccessCredential> accessCredentialDbHelper = null;
            boolean flag = false;
            try {
                accessCredentialDbHelper = DbHelper.getConnection();
                AccessCredential accessCredential = accessCredentialDbHelper.readBySearchKey(AccessCredential.class, clientId);
                if (accessCredential == null) {
                    System.out.println("--- No stored token, fetching new token ---");
                    flag = true;
                }
                String token = accessCredential.getToken();
                ZoomAPI zoomAPI = new ZoomAPI(token, 0.2);
                String s = zoomAPI.getChatChannels().check();
                JSONObject jsonObject = new JSONObject(s);
                HashMap status = new Gson().fromJson(jsonObject.toString(), HashMap.class);
                if (status.containsKey("code") && (double) status.get("code") == 124) {
                    System.out.println("--- stored token expires, fetching new token ---");
                    flag = true;
                }
                setAccessToken(token);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                accessCredentialDbHelper.closeConnection();
            }
            return flag;
        }
    }



