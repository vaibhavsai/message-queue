# A Basic message queue implementation

## Scope

1. Capability to Create Topics.
2. A `Publisher` to a topic should be able to push a message to that topic.
3. A `Subscriber` of a topic should be able to consume messages from that topic.
4. Every `Subscriber` of a topic, must be able to see all messages published to that topic.
5. Capability to have multiple concurrent `Subscribers`
6. Capability to replay messages in a topic from a specified `offset`
