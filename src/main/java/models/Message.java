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
    private String sender;

    @Column
    private String dateTime;

    @Column
    private String message;

    @Column
    private int timeStamp;

    @Column
    private String channelId;

    public Message() {}

    public Message(long pKey, String messageId, String message, String sender, String dateTime, int timeStamp, String channelId) {
        this.pKey = pKey;
        this.messageId = messageId;
        this.message = message;
        this.sender = sender;
        this.dateTime = dateTime;
        this.timeStamp = timeStamp;
        this.channelId = channelId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "pKey=" + pKey +
                ", messageId='" + messageId + '\'' +
                ", sender='" + sender + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", message='" + message + '\'' +
                ", timeStamp=" + timeStamp +
                ", channelId='" + channelId + '\'' +
                '}';
    }
}
