package multi.server3;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/server4")
public class ServerController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RedisTemplate<String, UserRedis> redisTemplate;
    private final InterProcessMutex lock;
    private boolean isEnd;

    @Autowired
    public ServerController(KafkaTemplate<String, String> kafkaTemplate,
                            RedisTemplate<String, UserRedis> redisTemplate,
                            InterProcessMutex lock) {
        this.kafkaTemplate = kafkaTemplate;
        this.redisTemplate = redisTemplate;
        this.lock = lock;
        this.isEnd = false;
    }

    @GetMapping("/hotdeal")
    public ResponseEntity<?> get(@RequestParam String userID) {
        try {
            UserRedis user = redisTemplate.opsForValue().get(userID);
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/end")
    public ResponseEntity<?> end() {
        if (!isEnd) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @EventListener
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("onMessageReceived");
        synchronized (this) {
            if (isEnd) {
                return;
            }
            try {
                if (lock.acquire(0, TimeUnit.SECONDS)) {
                    try {
                        isEnd = true;
                        kafkaTemplate.send("get", "get");
                        Thread.sleep(500);
                    } finally {
                        lock.release();
                    }
                } else {
                    isEnd = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
