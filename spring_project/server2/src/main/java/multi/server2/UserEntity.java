package multi.server2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userID;
    private LocalDateTime curTime;

    public UserEntity() {}

    public UserEntity(String userID, LocalDateTime curTime) {
        this.userID = userID;
        this.curTime = curTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public UserRedis toUserRedis() {
        return new UserRedis(userID, curTime);
    }
}
