package Utils;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;

public class JwtClient implements Auth {

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void authorize() throws OAuthSystemException, IOException, OAuthProblemException {

    }
}
