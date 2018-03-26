package com.effectivenorth.kafkaspike

import com.effectivenorth.kafkaspike.model.FixtureJSON
import mu.KotlinLogging
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.apache.kafka.streams.Consumed
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Produced
import java.lang.Character.toUpperCase
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.serialization.Serde
import java.util.*
import org.apache.kafka.streams.KafkaStreams




@SpringBootApplication
class Application {

    val logger = KotlinLogging.logger {}
    val builder = StreamsBuilder()

    init {
        logger.debug { "Starting Kafka Spike" }

        createTopology()
    }

    fun createTopology() {

        //create the bare minimum configuration
        val props = Properties()
        props[StreamsConfig.APPLICATION_ID_CONFIG] = "yelling_app_id" //unique id of the stream in the cluster
        props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092" //the kafka cluster we're going to use
        val streamsConfig: StreamsConfig = StreamsConfig(props)

        //define our serializer/deserializer that can be used by our stream
        val stringSerde = Serdes.String()

        //setup the source node - - note that if we dont supply Serde's then the ones defined in config will be used
        val simpleFirstStream = builder.stream<String, String>("src-topic", Consumed.with<String, String>(stringSerde, stringSerde))

        //setup a processing node - do something with each message we see
        val upperCasedStream: KStream<String, String> = simpleFirstStream.mapValues({v -> v.toUpperCase()})

        //setup a sink node - note that if we dont supply Serde's then the ones defined in config will be used
        upperCasedStream.to("out-topic", Produced.with(stringSerde, stringSerde))

        //build our stream
        val kafkaStreams = KafkaStreams(builder.build(), streamsConfig)

        //get the stream running
        kafkaStreams.start()
        Thread.sleep(35000)
        logger.debug { "Shutting down the YELLING stream now..." }
        kafkaStreams.close()

    }
}



fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}


//A record in a stream is made up of two components, a key and a value.  Both these components in the record
//are serialised as they are put onto the Kafka Topic (for efficiency) and must be deserialised before they can
//be used by an application.  The 'serde' (Serializer/Deserializer) is used to tell Kafka how it should perform
//these operations.

//Once a message is submitted to the stream, it can be manipulated by the operations that make up the stream.