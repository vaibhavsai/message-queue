package messagequeue.worker;

import lombok.SneakyThrows;
import messagequeue.domain.Message;
import messagequeue.domain.Topic;
import messagequeue.service.Subscriber;

/*
Takes care of relaying messages to specific subscriber
 */
public class SubscriberWorker implements Runnable{

    private final Topic topic;
    private final Subscriber subscriber;

    public SubscriberWorker(final Topic topic, final Subscriber subscriber) {
        this.topic = topic;
        this.subscriber = subscriber;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (subscriber){
            while(true){
                int currOffset = subscriber.getOffset().get();
                while(currOffset >= topic.getMessages().size()){
                    subscriber.wait();
                }
                Message message = topic.getMessages().get(currOffset);
                subscriber.process(message);
                subscriber.getOffset().compareAndSet(currOffset, currOffset+1);
            }
        }
    }

    public void resume(){
        synchronized (subscriber) {
            subscriber.notify();
        }
    }
}
