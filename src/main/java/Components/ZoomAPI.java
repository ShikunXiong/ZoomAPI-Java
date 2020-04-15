package Components;

public class ZoomAPI {
    private ChatChannels chatChannels;
    private ChatMessages chatMessages;

    public ZoomAPI(String token){
        this.chatChannels = new ChatChannels(token);
        this.chatMessages = new ChatMessages(token);
    }
    public ChatChannels getChatChannels() {
        return chatChannels;
    }
    public ChatMessages getChatMessages() { return chatMessages; }
}

