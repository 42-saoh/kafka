package multi.server3;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("User")
public class UserRedis implements Serializable {
    private String userID;
    private LocalDateTime timestamp;

    public UserRedis() {}

    public UserRedis(String userID, LocalDateTime timestamp) {
        this.userID = userID;
        this.timestamp = timestamp;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

