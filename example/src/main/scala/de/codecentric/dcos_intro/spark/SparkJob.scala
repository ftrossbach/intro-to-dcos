package de.codecentric.dcos_intro.spark


import de.codecentric.dcos_intro.{Tweet, TweetDecoder}
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import com.datastax.spark.connector.streaming._

/**
  * Created by ftr on 08/04/16.
  */
object SparkJob {

  def main(args: Array[String]) {

    val consumerTopic = args(0)
    val sparkConf = new SparkConf()
      .setAppName(getClass.getName)
      .set("spark.cassandra.connection.host", s"${args(1)}")
      .set("spark.cassandra.connection.port", s"${args(2)}")
    val consumerProperties = Map("bootstrap.servers" -> args(3), "auto.offset.reset" -> "smallest")
    val ssc = new StreamingContext(sparkConf, Seconds(1))

    val kafkaStream = KafkaUtils.createDirectStream[String, Tweet, StringDecoder, TweetDecoder](
      ssc,
      consumerProperties,
      Set(consumerTopic)
    )

    kafkaStream.map(tuple => tuple._2).saveToCassandra("dcos", "tweets")

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}
