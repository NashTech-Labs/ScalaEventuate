name := "ScalaEventuate"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "OJO Snapshots" at "https://oss.jfrog.org/oss-snapshot-local"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.1.2",
  "com.rbmhtechnology" %% "eventuate-core" % "0.9-SNAPSHOT",
  "com.rbmhtechnology" %% "eventuate-log-cassandra" % "0.9-SNAPSHOT",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.11"
)