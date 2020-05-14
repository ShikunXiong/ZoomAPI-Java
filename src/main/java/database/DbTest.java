package database;

import models.Message;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class DbTest {
    public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        DbHelper<Message> messageHelper =DbHelper.getConnection();
        // Message m1 = new Message("0001", "5001", "I love national parks", 2);
        //messageHelper.write(m1);
        List<Message> messages = messageHelper.read(Message.class);
        for (Message m : messages) {
            System.out.println(m);
        }
    }
}
