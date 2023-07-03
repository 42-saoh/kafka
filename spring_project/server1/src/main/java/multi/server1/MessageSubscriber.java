package multi.server1;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class MessageSubscriber implements MessageListener {

    private final ApplicationEventPublisher eventPublisher;

    public MessageSubscriber(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        eventPublisher.publishEvent(new MessageReceivedEvent(this));
    }
}
