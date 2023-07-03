package multi.server3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {
    private final RedisConnectionFactory redisConnectionFactory;
    private final ChannelTopic topic;
    private final ApplicationEventPublisher eventPublisher;


    @Autowired
    public RedisConfig(RedisConnectionFactory redisConnectionFactory,
                       ChannelTopic topic,
                       ApplicationEventPublisher eventPublisher
    ) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.topic = topic;
        this.eventPublisher = eventPublisher;
    }

    @Bean
    public RedisTemplate<String, UserRedis> redisTemplate(RedisConnectionFactory connectionFactory) {
        ObjectMapper mapper = new ObjectMapper();
        Jackson2JsonRedisSerializer<UserRedis> serializer = new Jackson2JsonRedisSerializer<>(UserRedis.class);
        RedisTemplate<String, UserRedis> template = new RedisTemplate<>();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        serializer.setObjectMapper(mapper);
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(serializer);
        return template;
    }

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new MessageSubscriber(eventPublisher));
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(MessageListenerAdapter messageListenerAdapter) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, topic);
        return container;
    }
}
