package Components;

import Utils.HttpUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ChatMessages {
    String token;
    HttpUtils util;
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public ChatMessages(String token){
        this.token = token;
        this.util = new HttpUtils();
    }

    public void listUserChatMessage(String... strs) throws IOException {
        if (strs.length != 1) return;
        // Build query
        String url = "/chat/users/me/messages"
                +"?page_size=" + "10"
                +"&to_channel=" + strs[0];

        // Parse data
        String data = this.util.getRequest(url, this.token);
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("messages");
        String[] ids = new String[jsonArray.length()];
        String[] messages = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = jsonArray.getJSONObject(i);
            ids[i] = jo.getString("id");
            messages[i] = jo.getString("message");
        }
        System.out.println("Messages ids are : ");
        System.out.println(Arrays.toString(ids));
        System.out.println("Messages are : ");
        System.out.println(Arrays.toString(messages));
    }

    public void sendChatMessage(String... strs) throws IOException {
        String url = "/chat/users/me/messages";
        // Get update message
        String message = "";
        System.out.println("Please input the message:   ");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
            message = sc.nextLine();
        }

        // Build post body
        JSONObject json = new JSONObject();
        json.put("message", message);
        json.put("to_channel", strs[0]);
        RequestBody body = RequestBody.create(JSON, json.toString());
        this.util.postRequest(url, this.token, body);
    }

    public void updateChatMessage(String...strs) throws IOException {
        String url = "/chat/users/me/messages/%s";  // messageid
        String messageID = null;
        String updateMessage = null;
        Scanner sc = new Scanner(System.in);
        String channelID = strs[0];
        System.out.println("All messages are :  ");
        listUserChatMessage(channelID);
        System.out.println("Please input the message id: ");
        if (sc.hasNextLine()) {
            messageID = sc.nextLine();
        }
        url = String.format(url, messageID);
        System.out.println(url);
        System.out.println("Please input the update message: ");
        if (sc.hasNextLine()) {
            updateMessage = sc.nextLine();
        }
        JSONObject json = new JSONObject();
        json.put("message", updateMessage);
        json.put("to_channel", channelID);
        RequestBody body = RequestBody.create(JSON, json.toString());
        this.util.putRequest(url, this.token, body);
    }

    public void deleteChatMessage(String...strs) throws IOException {
        String url = "/chat/users/me/messages/%s"
                   + "?to_channel=" + strs[0];
        System.out.println("All messages are :  ");
        listUserChatMessage(strs[0]);
        String messageID = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please input the message id: ");
        if (sc.hasNextLine()) {
            messageID = sc.nextLine();
        }
        url = String.format(url, messageID);
        this.util.deleteRequest(url, this.token);
    }
}
