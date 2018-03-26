package com.effectivenorth.kafkaspike.utils

import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class TestProducer {

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, String>

    val logger = KotlinLogging.logger {}

    fun send(topic: String, payload: String) {
        logger.debug { "sending payload='$payload' to topic='$topic'" }
        kafkaTemplate.send(topic, payload)
    }
}