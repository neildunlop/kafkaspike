package com.effectivenorth.kafkaspike

import com.effectivenorth.kafkaspike.utils.TestProducer
import com.effectivenorth.kafkaspike.utils.TestReceiver
import org.awaitility.Awaitility.await
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.rule.KafkaEmbedded
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import java.util.concurrent.TimeUnit


//A simple test that uses an embedded kafka instance, a test producer, a test receiver and awaitility to test that
//we can send and receive messages using Kafka.
@DirtiesContext
@RunWith(SpringRunner::class)
@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"])
class EmbeddedKafkaTest {

    @Autowired
    val receiver = TestReceiver()

    @Autowired
    val producer = TestProducer()


    //@Value("\${kafkatest.topic}")
    val topic = "myTopic"


    companion object {
        @ClassRule
        @JvmField
        val embeddedKafka = KafkaEmbedded(1, true, 0)

        @JvmStatic
        @BeforeClass
        fun setUpClass() {
            //FIXME: Couldn't find kafka server configurations - kafka server is listening on a random port so i overwrite the client config here should be other way around
            System.setProperty("spring.kafka.bootstrap-servers", embeddedKafka.brokersAsString)
            System.setProperty("spring.cloud.stream.kafka.binder.zkNodes", embeddedKafka.zookeeperConnectionString)
        }
    }

    @Test
    fun testSendAndReceive() {
        val testPayload = "Hello World!"

        producer.send(topic, testPayload)
        await().atMost(5, TimeUnit.SECONDS).until { receiver.latch.count == 0L }

        //assertThat(receiver.latch.count, equalTo(0L))
        assertThat(receiver.receivedMessages.size, equalTo(1))
        assertThat(receiver.receivedMessages[0].value().toString(), equalTo(testPayload))
    }
}