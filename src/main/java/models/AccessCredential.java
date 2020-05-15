package models;

import database.annotation.Column;
import database.annotation.DatabaseTable;
import database.annotation.PrimaryKey;
import database.annotation.SearchKey;

@DatabaseTable("Credentials")
public class AccessCredential {

    @PrimaryKey
    private long pKey;
    @SearchKey
    @Column
    private String clientId;
    @Column
    private String token;

    public AccessCredential() {}

    public AccessCredential(long pKey, String clientId, String token) {
        this.pKey = pKey;
        this.clientId = clientId;
        this.token = token;
    }

    public long getpKey() {
        return pKey;
    }

    public void setpKey(long pKey) {
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

    @Override
    public String toString() {
        return "Credential{" +
                "pKey='" + pKey + '\'' +
                ", clientId='" + clientId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
