package multi.server1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

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
