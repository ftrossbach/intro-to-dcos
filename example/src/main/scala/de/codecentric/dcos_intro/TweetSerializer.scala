package de.codecentric.dcos_intro


import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.util


import org.apache.kafka.common.serialization._;

/**
  * Created by ftr on 03/04/16.
  */
class TweetSerializer extends Serializer[Tweet]{
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def serialize(topic: String, data: Tweet): Array[Byte] = {

    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(data)
    oos.close

    return baos.toByteArray


  }

  override def close(): Unit = {}
}
