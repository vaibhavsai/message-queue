Message Queue
We have to design a message queue supporting following requirements.

Ability to Create Topics

Publisher to a topic should be able to push a message to that topic

Subscribers should be able to consume messages from their topic

Whenever a message is published to topic, all its subscribers must see that message

Ability to run Subscribers parallely

Ability to replay messages in a topic from a specified offset