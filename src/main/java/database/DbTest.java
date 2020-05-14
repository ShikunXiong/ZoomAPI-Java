package database;

import models.Message;

import java.sql.SQLException;

public class DbTest {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        Message m1 = new Message("0001", "5001", "I love national parks", 2);
        DbHelper<Message> messageHelper =DbHelper.getConnection();
        messageHelper.write(m1);
    }
}
