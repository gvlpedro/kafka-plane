package usecase

import java.net.URI
import java.time.Instant
import java.util.Properties

import scala.concurrent.Await
import scala.concurrent.duration._
import org.apache.kafka.clients.producer.ProducerConfig
import usecase.io.Producer
import usecase.model.{Id, User}

object API extends App {

  val config = new Properties()
  config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BootstrapServers)

  // Send a user
  val userProducer = Producer[User](config)

  val user = User(
    Id[User]("user0"),
    Instant.now(),
    URI.create("https://some-uri"),
    "Test",
    verified = false,
    deleted = false
  )
  Await.result(userProducer.send(user), 1.minute)

  userProducer.close()
}
