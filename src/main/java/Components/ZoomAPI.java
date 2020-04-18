package Components;

import Utils.AccessLimitService;

public class ZoomAPI {
    private ChatChannels chatChannels;
    private ChatMessages chatMessages;
    private meeting meeting;
    private AccessLimitService accessLimitService;

    public ZoomAPI(String token, double limit){
        this.chatChannels = new ChatChannels(token);
        this.chatMessages = new ChatMessages(token);
        this.meeting = new meeting(token);
        this.accessLimitService = new AccessLimitService(limit);
    }
    public ChatChannels getChatChannels() { return chatChannels; }
    public ChatMessages getChatMessages() { return chatMessages; }
    public meeting getMeeting() {return meeting; }

    public AccessLimitService getAccessLimitService() {
        return accessLimitService;
    }
}

