package Utils;

import com.google.common.util.concurrent.RateLimiter;

public class AccessLimitService {

    RateLimiter rateLimiter;

    public AccessLimitService(double limit) {
        rateLimiter = RateLimiter.create(limit);
    }
    public void acquire(){
        rateLimiter.acquire();
    }
}

