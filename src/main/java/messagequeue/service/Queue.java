package messagequeue.service;


import lombok.NonNull;
import messagequeue.domain.Message;
import messagequeue.domain.Topic;

public interface Queue {
    Topic createTopic( final String topicName);
    void subscribe( final Subscriber subscriber,  final Topic topic);
    void publish( final Topic topic,  final Message message);
    void changeOffset( final Topic topic,  final Subscriber subscriber,  final Integer newOffset);//for replaying messages
}
