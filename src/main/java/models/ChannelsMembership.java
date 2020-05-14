package models;

import database.annotation.Column;
import database.annotation.DatabaseTable;
import database.annotation.PrimaryKey;

@DatabaseTable("ChannelsMembership")
public class ChannelsMembership {

    @PrimaryKey
    private long pKey;
    @Column
    private String channelId;
    @Column
    private String memberId;
    @Column
    private String email;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String role;

    public ChannelsMembership() {

    }

    public ChannelsMembership(long pKey, String channelId, String memberId, String email, String firstName, String lastName, String role) {
        this.pKey = pKey;
        this.channelId = channelId;
        this.memberId = memberId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public long getpKey() {
        return pKey;
    }

    public void setpKey(long pKey) {
        this.pKey = pKey;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "ChannelsMembership{" +
                "pKey='" + pKey + '\'' +
                ", channelId='" + channelId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
