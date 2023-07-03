package multi.server2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

public class ServerService {
    private final ObjectMapper objectMapper;
    private final ServerRepository serverRepository;
    @Qualifier("userRedisTemplate")
    private final RedisTemplate<String, UserRedis> redisTemplate;

    private final ChannelTopic topic;
    private boolean isEnd;

    public ServerService(ServerRepository serverRepository,
                         ObjectMapper objectMapper,
                         RedisTemplate<String, UserRedis> redisTemplate,
                         ChannelTopic topic) {
        this.serverRepository = serverRepository;
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.topic = topic;
        this.isEnd = false;
    }

    @KafkaListener(topics = "deal", groupId = "two")
    public void listen(String message) {
        if (isEnd)
            return ;
        try {
            UserEntity userEntity = objectMapper.readValue(message, UserEntity.class);
            userEntity = serverRepository.save(userEntity);
            if (userEntity.getId() == 11) {
                redisTemplate.convertAndSend(topic.getTopic(), message);
                isEnd = true;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "get", groupId = "two")
    public void listenEnd(String message) {
        if (message.equals("get")) {
            List<UserEntity> userEntities = serverRepository.findAllByOrderByTimestampAsc();
            for (UserEntity userEntity : userEntities) {
                redisTemplate.opsForValue().set(userEntity.getUserID(), userEntity.toUserRedis());
            }
        }
    }
}
