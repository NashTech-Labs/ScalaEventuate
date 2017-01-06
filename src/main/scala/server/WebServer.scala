package server

/**
  * Eventuate.Created by knoldus on 6/12/16.
  */
import Eventuate.{EventuateCommand, EventuateQuery}
import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.rbmhtechnology.eventuate.ReplicationEndpoint
import com.rbmhtechnology.eventuate.log.cassandra.CassandraEventLog
import handler.RequestHandler

import scala.io.StdIn


object WebServer extends Routes with App with RequestHandler{

  implicit val system = ActorSystem("akka-system")
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()
    val endpoint: ReplicationEndpoint =    {
      ReplicationEndpoint(id => CassandraEventLog.props(id))(system)
    }

    endpoint.activate()

    // Initialise event log
    val eventLog = endpoint.logs(ReplicationEndpoint.DefaultLogName)

    val command = system.actorOf(Props(new EventuateCommand(endpoint.id, eventLog)))
    val query = system.actorOf(Props(new EventuateQuery(endpoint.id, eventLog)))

    val interface = "localhost"
    val port = 8080

    val x = getRequest(command)

    val binding = Http().bindAndHandle(route(command,query), interface, port)
    Console.println(Console.GREEN +
      """
            ___   ___   ___  __   __  ___   ___
           / __| | __| | _ \ \ \ / / | __| | _ \
           \__ \ | _|  |   /  \ V /  | _|  |   /
           |___/ |___| |_|_\   \_/   |___| |_|_\

      """+Console.RESET)
      println(s"Server is now online at http://$interface:$port\nPress RETURN to stop...")
      StdIn.readLine()

      binding.flatMap(_.unbind()).onComplete(_ => system.terminate())
      println("Server is down...")


}
