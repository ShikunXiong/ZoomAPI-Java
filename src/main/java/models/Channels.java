package models;

import database.annotation.Column;
import database.annotation.PrimaryKey;

public class Channels {

    @PrimaryKey
    private String pKey;
    @Column
    private String channelId;
    @Column
    private String channelName;
    @Column
    private int channelType;

    public Channels(String channelId, String channelName, int channelType) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelType = channelType;
    }

    public String getpKey() {
        return pKey;
    }

    public void setpKey(String pKey) {
        this.pKey = pKey;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }
}
