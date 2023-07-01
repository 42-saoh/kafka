package multi.server3;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("User")
public class UserRedis implements Serializable {
    private String userID;
    private LocalDateTime curTime;

    public UserRedis() {}

    public UserRedis(String userID, LocalDateTime curTime) {
        this.userID = userID;
        this.curTime = curTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public LocalDateTime getCurTime() {
        return curTime;
    }

    public void setCurTime(LocalDateTime curTime) {
        this.curTime = curTime;
    }
}

