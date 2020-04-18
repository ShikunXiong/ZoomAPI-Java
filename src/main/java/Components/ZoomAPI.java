package Components;

import Utils.AccessLimitService;

public class ZoomAPI {
    private final ChatChannels chatChannels;
    private final ChatMessages chatMessages;
    private final AccessLimitService accessLimitService;

    public ZoomAPI(String token, double limit) {
        this.chatChannels = new ChatChannels(token);
        this.chatMessages = new ChatMessages(token);
        this.accessLimitService = new AccessLimitService(limit);
    }

    public ChatChannels getChatChannels() throws InterruptedException {
        if (this.accessLimitService.tryAcquire()) {
            return chatChannels;
        } else {
            System.out.println("Visited too frequently, wait for 1s");
            Thread.sleep(1000);
            return chatChannels;
        }
    }

    public ChatMessages getChatMessages() throws InterruptedException {
        if (this.accessLimitService.tryAcquire()) {
            return chatMessages;
        } else {
            System.out.println("Visited too frequently, wait for 1s");
            Thread.sleep(1000);
            return chatMessages;
        }
    }

    public AccessLimitService getAccessLimitService() {
        return accessLimitService;
    }
}

