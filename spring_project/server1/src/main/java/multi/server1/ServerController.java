package multi.server1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/server1")
public class ServerController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private boolean isEnd;


    @Autowired
    public ServerController(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.isEnd = false;
    }

    @RequestMapping("/hotdeal")
    public ResponseEntity<?> get() {
        System.out.println("request get : " + isEnd);
        if (isEnd) {
            return ResponseEntity.ok().build();
        }
        User user = new User(UUID.randomUUID().toString(), LocalDateTime.now());
        try {
            String userJson = objectMapper.writeValueAsString(user);
            System.out.println(userJson);
            kafkaTemplate.send("deal", userJson);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @KafkaListener(topics = "end", groupId = "one")
    public void listen(String message) {
        System.out.println("end!!############# : " + message);
        if (message.equals("end")) {
            synchronized (this) {
                isEnd = true;
            }
        }
    }
}
