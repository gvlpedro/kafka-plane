package example

import java.util

import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsConfig

import scala.collection.JavaConverters._

class Consumer {

  def main(args: Array[String]): Unit = {
    consumeFromKafka("quick-start")
  }

  private def consumeFromKafka(topic: String) = {

    val props = new Properties()
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Conf.Brokers)
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Conf.stringSerde)
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Conf.stringSerde)
    props.put("auto.offset.reset", "latest")
    props.put("group.id", "consumer-group")

    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Arrays.asList(topic))

    while (true) {
      val record = consumer.poll(1000).asScala
      for (data <- record.iterator)
        println(data.value())
    }
  }
}
