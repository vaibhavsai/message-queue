package messagequeue.domain;

import lombok.Data;
import lombok.NonNull;
import messagequeue.service.Subscriber;

import java.util.ArrayList;
import java.util.List;

/*
Holds metadata, messages, subscribers
 */
@Data
public class Topic {
    private final String id;
    private final String name;
    private final List<Message> messages;
    private final List<Subscriber> subscribers;

    public Topic(String id, String name) {
        this.id = id;
        this.name = name;
        messages = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    //implement non-blocking way to do this
    public synchronized void addMessage(final Message message) {
        messages.add(message);
    }
    public void addSubscriber(final Subscriber subscriber) {
        subscribers.add(subscriber);
    }

}
