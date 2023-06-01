package messagequeue.service.impl;

import messagequeue.domain.Message;
import messagequeue.domain.Topic;
import messagequeue.service.Queue;
import messagequeue.service.Subscriber;
import messagequeue.worker.TopicWorker;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class QueueImpl implements Queue {
    private final Map<String, TopicWorker> topicWorkerMap;

    public QueueImpl() {
        topicWorkerMap = new HashMap<>();
    }

    @Override
    public Topic createTopic(String topicName) {
        final Topic topic = new Topic(UUID.randomUUID().toString(), topicName);
        TopicWorker topicWorker = new TopicWorker(topic);
        topicWorkerMap.put(topic.getId(), topicWorker);
        System.out.println("Topic created with name: " + topic.getName() + " and id: " + topic.getId());
        return topic;
    }

    @Override
    public void subscribe(Subscriber subscriber, Topic topic) {
        topic.addSubscriber(subscriber);
        System.out.println("Added subscriber: " + subscriber.getId() + " to topic: " + topic.getName());
    }

    /*
    Adds message to topic's message list and performs sending of this message to applicable subscribers in separate thread
     */
    @Override
    public void publish(Topic topic, Message message) {
        topic.addMessage(message);
        System.out.println("Published message: " + message.getMessage() + " to topic: " + topic.getName());
        new Thread(() -> topicWorkerMap.get(topic.getId()).pushMessage()).start();
    }

    @Override
    public void changeOffset(Topic topic, Subscriber subscriber, Integer newOffset) {
        for (Subscriber sub : topic.getSubscribers()) {
            if (Objects.equals(sub.getId(), subscriber.getId())) {
                sub.getOffset().set(newOffset);
                System.out.println("Offset changed to: " + newOffset + " for subscriber: " + sub.getId());
                new Thread(() -> topicWorkerMap.get(topic.getId()).pushMessageToSubscriber(sub)).start();
                break;
            }
        }
    }
}
