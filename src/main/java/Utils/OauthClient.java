package Utils;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class OauthClient {
    String token = "";
    public String getToken(){
        return this.token;
    }

    public void ApplyToken() throws OAuthSystemException, IOException, OAuthProblemException {
        Properties props = new Properties();
        props.load(new java.io.FileInputStream("src/settings.properties"));
        String browser_path = props.getProperty("browser_path");
        String authorize_url = props.getProperty("authorize_url");

        List<String> cmd = new ArrayList<String>();
        cmd.add(browser_path);
        cmd.add(authorize_url);
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
                .tokenLocation(props.getProperty("token_url"))
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(props.getProperty("client_id"))
                .setClientSecret(props.getProperty("client_secret"))
                .setRedirectURI(props.getProperty("redirect_url"))
                .setCode(code)
                .buildQueryMessage();
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(ClientRequest, "POST");

        String accessToken = oAuthResponse.getAccessToken();
        //Long expiresIn = oAuthResponse.getExpiresIn();
        this.token = accessToken;
    }
}
