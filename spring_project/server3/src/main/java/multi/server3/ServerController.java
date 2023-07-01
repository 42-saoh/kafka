package multi.server3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/server4")
public class ServerController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RedisTemplate<String, UserRedis> redisTemplate;
    private boolean isEnd;

    @Autowired
    public ServerController(KafkaTemplate<String, String> kafkaTemplate,
                            RedisTemplate<String, UserRedis> redisTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.redisTemplate = redisTemplate;
        this.isEnd = false;
    }

    @GetMapping("/hotdeal")
    public ResponseEntity<?> get(@RequestParam String userID) {
        System.out.println("request get : " + userID);
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
        System.out.println("request end");
        if (!isEnd) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @KafkaListener(topics = "end", groupId = "three")
    public void listen(String message) {
        synchronized (this) {
            if (isEnd)
                return ;
            if (message.equals("end")) {
                System.out.println("get!!#############");
                isEnd = true;
                kafkaTemplate.send("get", "get");
            }
        }
    }
}
