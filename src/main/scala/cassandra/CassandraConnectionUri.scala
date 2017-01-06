package cassandra

import com.typesafe.config.ConfigFactory
import scala.collection.JavaConversions._

/**
  * Eventuate.Created by knoldus on 4/1/17.
  */

object CassandraConnectionUri {

  val configFactory = ConfigFactory.load
  val port = configFactory.getInt("eventuate.log.cassandra.default-port")
  val hosts = configFactory.getStringList("eventuate.log.cassandra.contact-points").toList
  val keyspace = configFactory.getString("eventuate.log.cassandra.keyspace")
  val replicationFactor = configFactory.getString("eventuate.log.cassandra.replication-factor").toInt
  val readConsistency = configFactory.getString("eventuate.log.cassandra.read-consistency")
  val writeConsistency = configFactory.getString("eventuate.log.cassandra.write-consistency")
  val removeKeyspace = configFactory.getBoolean("removeKeySpace")

  val userKeyspace = configFactory.getString("cassandra.keyspace")
  val userTable = configFactory.getString("cassandra.tablename")
  val concurrencyLevel = configFactory.getInt("app.concurrency")
}

