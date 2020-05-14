package models;

import database.annotation.Column;
import database.annotation.DatabaseTable;
import database.annotation.PrimaryKey;
import database.annotation.SearchKey;

@DatabaseTable("Messages")
public class Message {

    @PrimaryKey
    private long pKey;

    @SearchKey
    @Column
    private String messageId;

    @Column
    private String dateTime;

    @Column
    private String message;

    @Column
    private int timeStamp;

    public Message() {}

    public Message(long pKey, String messageId, String dateTime, String message, int timeStamp) {
        this.pKey = pKey;
        this.messageId = messageId;
        this.dateTime = dateTime;
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
        return dateTime;
    }

    public void setDataTime(String dateTime) {
        this.dateTime = dateTime;
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

    @Override
    public String toString() {
        return "Message{" +
                "pKey=" + pKey +
                ", messageId='" + messageId + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", message='" + message + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
