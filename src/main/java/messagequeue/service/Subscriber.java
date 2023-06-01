package messagequeue.service;

import messagequeue.domain.Message;

import java.util.concurrent.atomic.AtomicInteger;

public interface Subscriber {
    String getId();
    void process(Message message) throws InterruptedException;

    AtomicInteger getOffset();
}
