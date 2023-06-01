package messagequeue.worker;

import messagequeue.domain.Topic;
import messagequeue.service.Subscriber;

import java.util.HashMap;
import java.util.Map;

/*
Takes care of relaying messages to subscribers of topic
 */
public class TopicWorker {
    private final Topic topic;
    private final Map<String, SubscriberWorker> subscriberWorkerMap;

    public TopicWorker(Topic topic) {
        this.topic = topic;
        this.subscriberWorkerMap = new HashMap<>();
    }

    public void pushMessage(){
        for(Subscriber subscriber: topic.getSubscribers()){
            pushMessageToSubscriber(subscriber);
        }
    }

    public void pushMessageToSubscriber(Subscriber subscriber){
        String subscriberId = subscriber.getId();
        if(!subscriberWorkerMap.containsKey(subscriberId)){
            SubscriberWorker subscriberWorker = new SubscriberWorker(this.topic, subscriber);
            subscriberWorkerMap.put(subscriberId, subscriberWorker);
            new Thread(subscriberWorker).start();
        }
        SubscriberWorker subscriberWorker = subscriberWorkerMap.get(subscriberId);
        subscriberWorker.resume();
    }
}
