package multi.server1;

import org.springframework.context.ApplicationEvent;

public class MessageReceivedEvent extends ApplicationEvent {
    public MessageReceivedEvent(Object source) {
        super(source);
    }
}