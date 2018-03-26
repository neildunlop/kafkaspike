package com.effectivenorth.kafkaspike

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.StreamsConfig
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration
import org.springframework.kafka.test.rule.KafkaEmbedded
import org.springframework.kafka.annotation.EnableKafkaStreams
//import org.hibernate.criterion.Projections.count
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@RunWith(SpringRunner::class)
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = ["TOPIC1", "TOPIC2"])
class KafkaStreamsTests {

    @Autowired
    private val embeddedKafka: KafkaEmbedded? = null

    @Test
    fun someTest() {
        val consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", this.embeddedKafka!!)
        consumerProps[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        val cf = DefaultKafkaConsumerFactory<Int, String>(consumerProps)
        val consumer = cf.createConsumer()
        this.embeddedKafka.consumeFromAnEmbeddedTopic(consumer, "TOPIC2")
        val replies = KafkaTestUtils.getRecords(consumer)
        assertThat(replies.count()).isGreaterThanOrEqualTo(1)
    }

    @Configuration
    @EnableKafkaStreams
    class KafkaStreamsConfiguration {

        @Value("\${" + KafkaEmbedded.SPRING_EMBEDDED_KAFKA_BROKERS + "}")
        private val brokerAddresses: String? = null

        @Bean(name = arrayOf(KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME))
        fun kStreamsConfigs(): StreamsConfig {
            val props = HashMap<String, Any>()
            props.put(StreamsConfig.APPLICATION_ID_CONFIG, "testStreams")
            props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, this.brokerAddresses!!)
            return StreamsConfig(props)
        }

    }

}