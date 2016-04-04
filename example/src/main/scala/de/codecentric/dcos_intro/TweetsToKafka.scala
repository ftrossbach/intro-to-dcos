package de.codecentric.dcos_intro



import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer

import akka.stream.scaladsl.{Flow, Sink, Source}
import com.softwaremill.react.kafka.{KeyValueProducerMessage, ProducerMessage, ProducerProperties, ReactiveKafka}
import com.typesafe.config.ConfigFactory
import org.apache.kafka.common.serialization.StringSerializer
import org.reactivestreams.Subscriber
import twitter4j._


object TweetsToKafka extends App {

  implicit val actorSystem = ActorSystem("actorSystem")
  implicit val materializer = ActorMaterializer()

  val config = ConfigFactory.load();

  val reactiveKafka = new ReactiveKafka()

  val subscriber: Subscriber[ProducerMessage[String, Tweet]] = reactiveKafka.publish(ProducerProperties(
    bootstrapServers = config.getString("dcos.kafka.host") + ":" + config.getString("dcos.kafka.port"),
    topic = config.getString("dcos.kafka.topic"),
    keySerializer = new StringSerializer(),
    valueSerializer = new TweetSerializer()
  ))



  val source = Source.actorPublisher[Tweet](Props[TweetPublisher])
  val ref: ActorRef = Flow[Tweet]
    .map[ProducerMessage[String, Tweet]] { elem: Tweet => {
    val sdf = new java.text.SimpleDateFormat("dd-MM-yyyy")
    val dateAsString = sdf.format(elem.date)
    new KeyValueProducerMessage[String, Tweet](dateAsString, elem)
  }
  }.to(Sink.fromSubscriber(subscriber))
    .runWith(source)


  var listener = new TwitterStatusListener(ref)
  var twitterStream = new TwitterStreamFactory().getInstance()
  twitterStream.addListener(listener)
  var query = new FilterQuery()
  query.language("de,en")
  query.track("a")
  twitterStream.filter(query)




}