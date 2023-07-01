package multi.server1;

import java.time.LocalDateTime;

public class User {
    private String userID;
    private LocalDateTime curTime;

    public User() {}

    public User(String userID, LocalDateTime curTime) {
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
