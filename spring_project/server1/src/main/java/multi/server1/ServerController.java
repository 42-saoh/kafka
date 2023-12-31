package multi.server1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
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

    @EventListener
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("onMessageReceived");
        if (isEnd) {
            return;
        }
        synchronized (this) {
            isEnd = true;
        }
    }

    @RequestMapping("/hotdeal")
    public ResponseEntity<?> get() {
        if (isEnd) {
            return ResponseEntity.ok().build();
        }
        User user = new User(UUID.randomUUID().toString(), LocalDateTime.now());
        try {
            String userJson = objectMapper.writeValueAsString(user);
            kafkaTemplate.send("deal", userJson);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
