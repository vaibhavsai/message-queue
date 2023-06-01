import messagequeue.domain.Message;
import messagequeue.domain.Topic;
import messagequeue.service.Subscriber;
import messagequeue.service.impl.QueueImpl;
import messagequeue.service.impl.SubscriberImpl;

public class TestMq {
    public static void main(String[] args) {
        QueueImpl queue = new QueueImpl();
        Topic topic1 = queue.createTopic("t1");
        Topic topic2 = queue.createTopic("t2");
        Subscriber sub1 = new SubscriberImpl("sub1", "First Subscriber", 10000L);
        Subscriber sub2 = new SubscriberImpl("sub2", "Second Subscriber", 5000L);
        Subscriber sub3 = new SubscriberImpl("sub3", "Third Subscriber",10000L);

        queue.subscribe(sub1, topic1);
        queue.subscribe(sub2, topic1);
        queue.subscribe(sub3, topic2);

        queue.publish(topic1, new Message("A"));
        queue.publish(topic1, new Message("B"));
        queue.publish(topic2, new Message("C"));

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException during sleep");
        }

        queue.publish(topic2, new Message("D"));
        queue.publish(topic1, new Message("E"));

//        queue.changeOffset(topic1, sub1, 0);
//        queue.changeOffset(topic1, sub2, 0);
    }
}
