package example

import java.util.Properties
import java.util.concurrent.CountDownLatch
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.KStream

class Streams {

  def main(args: Array[String]): Unit = {

    val props: Properties = new Properties()
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "inventory-data")
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Conf.Brokers)
    props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0)
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Conf.stringSerde)
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Conf.stringSerde)

    // Get the source stream.
    val builder = new StreamsBuilder()
    val source  = builder.stream[String, String](Conf.inventoryPurchases)

    // Convert the value from String to Integer. If the value is not a properly-formatted number, print a message and set it to 0.
    val integerValuesSource: KStream[String, Int] = source.mapValues(_.toString.toInt)

    // Group by the key and reduce to provide a total quantity for each key.
    val productCounts = integerValuesSource.groupByKey.reduce(_ + _)

    // Output to the output topic.
    productCounts.toStream().to("total_purchases")

    val topology = builder.build()
    val streams  = new KafkaStreams(topology, props)

    val latch = new CountDownLatch(1)
    streams.start()
    latch.await()

    // Print the topology to the console.
    println(topology.describe())

    // Attach a shutdown handler to catch control-c and terminate the application gracefully.
    Runtime.getRuntime.addShutdownHook(new Thread {
      override def run(): Unit = {
        streams.close()
        latch.countDown()
      }
    })
  }
}
