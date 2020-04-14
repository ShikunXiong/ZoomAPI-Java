package Bots;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;

public class test2{
    AuthorizationCodeFlow flow;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = flow.newAuthorizationUrl().setState("xyz")
                .setRedirectUri("https://api.zoom.us/oauth/token").build();
        response.sendRedirect(url);
    }
    public static void main(String[] args) throws IOException {
        test2 t = new test2();

    }

}
