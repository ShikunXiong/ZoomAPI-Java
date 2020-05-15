package database;

import models.Credential;
import models.Message;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class DbTest {
    public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        /* tests in message db */
        DbHelper<Message> messageHelper = DbHelper.getConnection();
        Message m1 = new Message(10, "1215", "I like you!","Yilin", "5001", 23, "test");
        // 1. create
        messageHelper.write(m1);
        // 2. read
        // 2.1 read all
        List<Message> messages = messageHelper.read(Message.class);
        for (Message m : messages) {
            System.out.println(m);
        }
        System.out.println("-----------");
        // 2.2 read based on searchKey
        messages = messageHelper.read(Message.class, "1213");
        for (Message m : messages) {
            System.out.println(m);
        }
        System.out.println("-----------");
        // 2.3 read based on primaryKey
        Message ms = messageHelper.read(Message.class, 1);
        System.out.println(ms);
        System.out.println("-----------");
        // 2.4 read based on designated field
        messages = messageHelper.read(Message.class, "sender", "Yilin");
        for (Message m : messages) {
            System.out.println(m);
        }
        System.out.println("-----------");
        // 3. update
        // 3.1 directly update object
//        Message m2 = new Message(10, "0111", "5111", "I'm Updated", 123, "Xizi");
//        messageHelper.update(m2);
//        // 3.2 update object based on search key and update the area.
//        messageHelper.update(Message.class, "3333", "message", "I love Dalian");
//        // 4. delete
//        // 4.1 directly
//        messageHelper.delete(m2);
//        // 4.2 delete by field name and value
//        messageHelper.delete(Message.class, "messageId", "0011");
//
//        /* tests in credential db */
//        DbHelper<Credential> credentialHelper = DbHelper.getConnection();
//        Credential c1 = new Credential(0, "client1", "token1");
//        credentialHelper.write(c1);
//        List<Credential> credentials = credentialHelper.read(Credential.class);
//        for (Credential c : credentials) {
//            System.out.println(c);
//        }
//        System.out.println("-----------");

        messageHelper.closeConnection();
    }
}
