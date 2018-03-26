package com.effectivenorth.kafkaspike

import com.effectivenorth.kafkaspike.model.FixtureJSON
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.processor.WallclockTimestampExtractor
import java.util.HashMap
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.connect.json.JsonDeserializer
import org.apache.kafka.connect.json.JsonSerializer
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Serialized
//import org.hibernate.criterion.Projections.count
import org.springframework.stereotype.Component


//@Component
//class StreamIngest {
//
//    val builder = StreamsBuilder()
//
//    fun fixtureStreamConfig(): StreamsConfig {
//        val props = HashMap<String, Any>()
//        props[StreamsConfig.APPLICATION_ID_CONFIG] = "fixtureStream"
//        props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.Integer().javaClass
//        props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = FixtureJSON::class.java
//        props[StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG] = WallclockTimestampExtractor().javaClass
//        props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = "kafka-broker1:9092"
//        props[StreamsConfig.COMMIT_INTERVAL_MS_CONFIG] = 1000
//
//        //not sure this should be here.. its client config
//        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
//
//        return StreamsConfig(props)
//    }
//
//    fun fixtureStream() {
//        val fixtureSerde = Serdes.serdeFrom(JsonSerializer(), JsonDeserializer())
//        val fixturesJSON = builder.stream<String, FixtureJSON>("input_fixture_topic")
//        print(fixturesJSON)
//
//        //parse a record so we have the Id of the fixture and the marketCount from each message.
//        val marketCounts = fixturesJSON.map({ k, v -> KeyValue<String, Int>(k, v.marketCount) })
//
//        // Count occurrences of each key (this is a KTable)
//        val countKeys = marketCounts.groupByKey(Serialized.with(Serdes.String(), Serdes.Integer()))
//                .count(Materialized.`as`("KeyStore"))
//                .toStream()
//        print(countKeys)
//    }
//
//
//}