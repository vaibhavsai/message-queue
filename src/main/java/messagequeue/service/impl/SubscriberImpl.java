package messagequeue.service.impl;

import messagequeue.domain.Message;
import messagequeue.service.Subscriber;

import java.util.concurrent.atomic.AtomicInteger;

public class SubscriberImpl implements Subscriber {

    private final String id;
    private final String name;
    private AtomicInteger offset;

    private final Long delayMillis;

    public SubscriberImpl(String id, String name, Long delayMillis) {
        this.id = id;
        this.name = name;
        this.offset = new AtomicInteger(0);
        this.delayMillis = delayMillis;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void process(Message message) throws InterruptedException{
        System.out.println(this.id + " Started Consuming Message: " + message.getMessage());
        Thread.sleep(this.delayMillis);
        System.out.println(this.id + " Finished Consuming Message: " + message.getMessage());
    }

    @Override
    public AtomicInteger getOffset() {
        return this.offset;
    }


}
