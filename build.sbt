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
      chill        % "test",
      avro4s       % "test"
    )
  )
