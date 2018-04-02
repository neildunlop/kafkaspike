package com.effectivenorth.kafkaspike.serializers

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer

class JsonDeserializer<T>: Deserializer<T> {

    private val objectMapper = ObjectMapper()
    private var deserializedClass: Class<T>? = null

    constructor()

    constructor(deserializedObject: Class<T>?) {
        this.deserializedClass = deserializedObject
    }
    
    override fun deserialize(topic: String?, data: ByteArray?): T? {
        if(data==null) {
            return null
        }
        return objectMapper.readValue(String(data), deserializedClass)

    }

    override fun close() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
        if(deserializedClass == null) {
            deserializedClass = configs?.get("serializedClass") as Class<T>
        }
    }
}