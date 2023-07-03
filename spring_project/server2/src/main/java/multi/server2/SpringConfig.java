package multi.server2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class SpringConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    @Bean
    public ServerService tigerService(ServerJPARepository serverJPARepository,
                                      ObjectMapper objectMapper,
                                      RedisTemplate<String, UserRedis> redisTemplate,
                                      ChannelTopic topic) {
        return new ServerService(serverJPARepository, objectMapper, redisTemplate, topic);
    }

    @Bean(name = "userRedisTemplate")
    public RedisTemplate<String, UserRedis> userRedisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        Jackson2JsonRedisSerializer<UserRedis> serializer = new Jackson2JsonRedisSerializer<>(UserRedis.class);
        RedisTemplate<String, UserRedis> template = new RedisTemplate<>();
        serializer.setObjectMapper(objectMapper);
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(serializer);
        return template;
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("end");
    }
}
