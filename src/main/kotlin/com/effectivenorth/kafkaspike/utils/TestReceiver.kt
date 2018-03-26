package com.effectivenorth.kafkaspike.utils

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch


@Component
class TestReceiver {

    val logger = KotlinLogging.logger {}
    val latch = CountDownLatch(1)
    val receivedMessages = mutableListOf<ConsumerRecord<*, *>>()

    //@KafkaListener(topics = ["\${kafkatest.topic}"])
    @KafkaListener(topics = ["myTopic"])
    fun receive(consumerRecord: ConsumerRecord<*, *>) {
        logger.debug("received payload='{}'", consumerRecord.toString())
        receivedMessages.add(consumerRecord)
        latch.countDown()
    }


}