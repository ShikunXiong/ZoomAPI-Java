package models;

import database.annotation.Column;
import database.annotation.DatabaseTable;
import database.annotation.PrimaryKey;

@DatabaseTable("Credentials")
public class Credentials {

    @PrimaryKey
    private String pKey;
    @Column
    private String clientId;
    @Column
    private String token;

    public Credentials(String clientId, String token) {
        this.clientId = clientId;
        this.token = token;
    }

    public String getpKey() {
        return pKey;
    }

    public void setpKey(String pKey) {
        this.pKey = pKey;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
