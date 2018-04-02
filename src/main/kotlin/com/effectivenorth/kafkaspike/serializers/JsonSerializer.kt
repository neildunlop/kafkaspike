package com.effectivenorth.kafkaspike.serializers

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serializer

//Custom serializer that uses GSON to serialise our Kafka Stream objects to JSON.
class JsonSerializer<T>: Serializer<T> {


    private val objectMapper = ObjectMapper()

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
    }

    override fun serialize(topic: String?, data: T): ByteArray {
        return objectMapper.writeValueAsBytes(data)
    }

    override fun close() {
    }
}