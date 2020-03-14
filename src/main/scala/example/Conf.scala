package example

import org.apache.kafka.common.serialization.Serdes

object Conf {
  val Brokers            = "localhost:9094"
  val stringSerde        = Serdes.String().getClass().getName()
  val inventoryPurchases = "inventory_purchases"
}
