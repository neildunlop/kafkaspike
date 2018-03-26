package com.effectivenorth.kafkaspike

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.TimeWindows
import org.apache.kafka.streams.processor.WallclockTimestampExtractor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration
import java.util.*


//@Configuration
//@EnableKafka
//@EnableKafkaStreams
//class KafkaStreamsConfiguration {
//
//    @Bean(name = [(KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)])
//    fun kStreamsConfig(): StreamsConfig {
//        val props = HashMap<String, Any>()
//        props[StreamsConfig.APPLICATION_ID_CONFIG] = "testStreams"
//        props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.Integer().javaClass
//        props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass
//        props[StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG] = WallclockTimestampExtractor().javaClass
//        props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = "kafka-broker1:9092"
//        return StreamsConfig(props)
//    }
//
//    @Bean
//    fun kStream(kStreamBuilder: StreamsBuilder): KStream<Int, String> {
//
//        val stream = kStreamBuilder.stream<Int, String>("streamingTopic1")
//        stream.mapValues(String::toUpperCase)
//                .groupByKey()
//                .reduce({ value1, value2 -> value1 + value2 }, TimeWindows.of(1000), "windowStore")
//                .toStream()
//                .map({ windowedId, value -> KeyValue(windowedId.key(), value) })
//                .filter({ i, s -> s.length > 40 })
//                .to("streamingTopic2")
//        stream.print()
//
//        return stream
//    }
//
//}