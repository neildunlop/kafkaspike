package com.effectivenorth.kafkaspike

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.rule.KafkaEmbedded
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class FixtureStreamTest {

    val logger = KotlinLogging.logger {}
    val BOOT_TOPIC = "testTopic"

    @Rule
    @JvmField
    final val embeddedKafka = KafkaEmbedded(1, true, BOOT_TOPIC)


    @Autowired
    lateinit var sender: KafkaTemplate<String, String>

//    val latch = CountDownLatch(3)
//
//    @KafkaListener(topics = arrayOf("testTopic"))
//    @Throws(Exception::class)
//    fun listen(cr: ConsumerRecord<*, *>) {
//        logger.info(cr.toString())
//        latch.countDown()
//    }

    @Test
    @Throws(Exception::class)
    fun testReceive() {
        sender.send(BOOT_TOPIC, "Hello Boot!")

//        val consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", this.embeddedKafka)
//        consumerProps[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
//        val cf = DefaultKafkaConsumerFactory<Int, String>(consumerProps)
//        val consumer = cf.createConsumer()
//        this.embeddedKafka.consumeFromAnEmbeddedTopic(consumer, BOOT_TOPIC)
//        val replies = KafkaTestUtils.getRecords(consumer)
//        assertTrue(replies.count() >= 1)
    }


}