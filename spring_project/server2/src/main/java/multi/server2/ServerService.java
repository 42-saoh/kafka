package multi.server2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

public class ServerService {
    private final ObjectMapper objectMapper;
    private final ServerRepository serverRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Qualifier("userRedisTemplate")
    private final RedisTemplate<String, UserRedis> redisTemplate;
    private boolean isEnd;

    public ServerService(ServerRepository serverRepository,
                         ObjectMapper objectMapper,
                         KafkaTemplate<String, String> kafkaTemplate,
                         RedisTemplate<String, UserRedis> redisTemplate) {
        this.serverRepository = serverRepository;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.redisTemplate = redisTemplate;
        this.isEnd = false;
    }

    @KafkaListener(topics = "deal", groupId = "two")
    public void listen(String message) {
        System.out.println("deal!!#############" + message);
        if (isEnd)
            return ;
        try {
            UserEntity userEntity = objectMapper.readValue(message, UserEntity.class);
            userEntity = serverRepository.save(userEntity);
            if (userEntity.getId() > 11) {
                System.out.println("end!!#############");
                kafkaTemplate.send("end", "end");
                isEnd = true;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "get", groupId = "two")
    public void listenEnd(String message) {
        System.out.println("get!!#############");
        if (message.equals("get")) {
            List<UserEntity> userEntities = serverRepository.findAllByOrderByTimestampAsc();
            for (UserEntity userEntity : userEntities) {
                redisTemplate.opsForValue().set(userEntity.getUserID(), userEntity.toUserRedis());
            }
        }
    }
}
