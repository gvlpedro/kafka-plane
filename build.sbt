import Dependencies._
import Versions._

ThisBuild / scalaVersion := Scala_2_12_Version
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "org.kafka-plane"
ThisBuild / organizationName := "own-project"

scalacOptions += "-Ypartial-unification"
resolvers += Opts.resolver.sonatypeSnapshots

testFrameworks += new TestFramework("minitest.runner.Framework")

lazy val root = (project in file("."))
  .settings(
    name := "kafka-plane",
    developers := List(Developer("", "Pedro García-Valenciano Leon", "--", url("https://github.com/gvlpedro"))),
    organizationHomepage := Option(url("http://ounity.com/")),
    libraryDependencies ++= Seq(
      kafkaStreams excludeAll (ExclusionRule("org.slf4j", "slf4j-log4j12"), ExclusionRule("org.apache.zookeeper",
                                                                                          "zookeeper")),
      scalaLogging % "test",
      logback      % "test",
      kafka        % "test" excludeAll (ExclusionRule("org.slf4j", "slf4j-log4j12"), ExclusionRule("org.apache.zookeeper",
                                                                                            "zookeeper")),
      curator      % "test",
      minitest     % "test",
      minitestLaws % "test",
      algebird     % "test",
      chill        % "test",
      avro4s       % "test"
    ) ++ circe
  )

lazy val circe = {
  val version = "0.11.1"
  Seq(
    "io.circe" %% "circe-core"    % version,
    "io.circe" %% "circe-parser"  % version,
    "io.circe" %% "circe-java8"   % version,
    "io.circe" %% "circe-generic" % version
  )
}
