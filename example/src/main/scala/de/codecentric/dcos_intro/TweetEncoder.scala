package de.codecentric.dcos_intro

import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.util

import kafka.serializer.Encoder
import org.apache.kafka.common.serialization._;


class TweetEncoder extends Encoder[Tweet] {

  override def toBytes(t: Tweet): Array[Byte] = {
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(t)
    oos.close

    return baos.toByteArray

  }
}
