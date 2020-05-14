package models;

import database.annotation.Column;
import database.annotation.DatabaseTable;
import database.annotation.PrimaryKey;

@DatabaseTable("Messages")
public class Messages {

    @PrimaryKey
    private long pKey;

    @Column
    private String messageId;

    @Column
    private String dataTime;

    @Column
    private String message;

    @Column
    private int timeStamp;

    public Messages(String messageId, String dataTime, String message, int timeStamp) {
        this.messageId = messageId;
        this.dataTime = dataTime;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public long getpKey() {
        return pKey;
    }

    public void setpKey(long pKey) {
        this.pKey = pKey;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}
