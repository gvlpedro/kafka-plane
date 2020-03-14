package example

import java.util.Properties

import org.apache.kafka.clients.producer._
import org.apache.kafka.streams.StreamsConfig

class Producer {

  def main(args: Array[String]): Unit = {
    writeToKafka(Conf.inventoryPurchases)
  }

  def writeToKafka(topic: String): Unit = {

    val props = new Properties()
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Conf.Brokers)
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Conf.stringSerde)
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Conf.stringSerde)

    val producer = new KafkaProducer[String, String](props)
    val record   = new ProducerRecord[String, String](topic, "key", "value")
    producer.send(record)
    producer.close()
  }
}
