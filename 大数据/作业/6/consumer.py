#!/usr/bin/python3

from kafka import KafkaConsumer

conf = {
    'bootstrap_servers': ["192.168.0.105:9092"],
    'topic_name': 'topicTest',
    'consumer_id': 'example-group1'
}

print('start consumer')
consumer = KafkaConsumer(conf['topic_name'],
                        bootstrap_servers=conf['bootstrap_servers'],
                        group_id=conf['consumer_id'])

for message in consumer:
    print("%s: value=%s" % (message.topic, message.value))

print('end consumer')
