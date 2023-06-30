package multi.server1;

import java.time.LocalDateTime;

public class User {
    private String userID;
    private LocalDateTime timestamp;

    public User() {}

    public User(String userID, LocalDateTime timestamp) {
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
