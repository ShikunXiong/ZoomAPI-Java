package models;

import database.annotation.Column;
import database.annotation.DatabaseTable;
import database.annotation.PrimaryKey;
import database.annotation.SearchKey;

@DatabaseTable("Channels")
public class Channel {

    @PrimaryKey
    private long pKey;
    @SearchKey
    @Column
    private String channelId;
    @Column
    private String channelJId;
    @Column
    private String channelName;
    @Column
    private int channelType;

    public Channel() {}

    public Channel(long pKey, String channelId, String channelJId, String channelName, int channelType) {
        this.pKey = pKey;
        this.channelId = channelId;
        this.channelJId = channelJId;
        this.channelName = channelName;
        this.channelType = channelType;
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

    public String getChannelJId() {
        return channelJId;
    }

    public void setChannelJId(String channelJId) {
        this.channelJId = channelJId;
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

    @Override
    public String toString() {
        return "Channel{" +
                "pKey=" + pKey +
                ", channelId='" + channelId + '\'' +
                ", channelJId='" + channelJId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", channelType=" + channelType +
                '}';
    }
}
