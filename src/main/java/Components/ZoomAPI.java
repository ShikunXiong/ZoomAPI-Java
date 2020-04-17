package Components;

import Utils.AccessLimitService;

public class ZoomAPI {
    private final ChatChannels chatChannels;
    private final ChatMessages chatMessages;
    private final AccessLimitService accessLimitService;

    public ZoomAPI(String token, double limit){
        this.chatChannels = new ChatChannels(token);
        this.chatMessages = new ChatMessages(token);
        this.accessLimitService = new AccessLimitService(limit);
    }
    public ChatChannels getChatChannels() {
        if (this.accessLimitService.tryAcquire()) {
            return chatChannels;
        } else {
            return null;
        }
    }
    public ChatMessages getChatMessages() {
        if (this.accessLimitService.tryAcquire()) {
            return chatMessages;
        } else {
            return null;
        }
    }
    public AccessLimitService getAccessLimitService() {
        return accessLimitService;
    }
}

