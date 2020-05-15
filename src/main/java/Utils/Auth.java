package Utils;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;
import java.sql.SQLException;

public interface Auth {
    public String Token = "";
    public String getToken();
    public void authorize() throws OAuthSystemException, IOException, OAuthProblemException, SQLException;
}
