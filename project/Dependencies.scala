import sbt._
import Versions._

object Dependencies {

  implicit class Exclude(module: ModuleID) {
    def log4jExclude: ModuleID =
      module.excludeAll(ExclusionRule("log4j"))

    def driverExclusions: ModuleID =
      module.log4jExclude
        .exclude("com.google.guava", "guava")
        .excludeAll(ExclusionRule("org.slf4j"))
  }

  val kafkaStreams = "org.apache.kafka"    % "kafka-streams" % KafkaVersion
  val kafkaClient  = "org.apache.kafka"    % "kafka-clients" % KafkaVersion
  val avro4s       = "com.sksamuel.avro4s" %% "avro4s-core"  % Avro4sVersion

  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging"  % ScalaLoggingVersion
  val logback      = "ch.qos.logback"             % "logback-classic" % LogbackVersion
  val chill        = "com.twitter"                %% "chill"          % ChillVersion // kryo serializer

}
