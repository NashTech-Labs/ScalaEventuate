name := "ScalaEventuate"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Eventuate Releases" at "https://dl.bintray.com/rbmhtechnology/maven"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
  "org.scalatest" %% "scalatest" % "2.2.0" % "test",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.1.2",
  "com.rbmhtechnology" %% "eventuate-core" % "0.8",
  "com.rbmhtechnology" %% "eventuate-log-cassandra" % "0.8",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.11"

)