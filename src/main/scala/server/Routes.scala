package server

import akka.actor.ActorRef
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.util.Timeout
import handler.RequestHandler

import scala.concurrent.duration.DurationInt
/**
  * Eventuate.Created by knoldus on 4/1/17.
  */
trait Routes extends RequestHandler{

  override val timeout = Timeout(40 seconds)
  case class User(userid: String, gender: String, nickname: String)

  def getRequest(query: ActorRef): Route =
    get {
      path("get") {
        parameters('userid)
        {
          (userId) =>
            val result = getUserHandler(query, userId)
            complete(result)
        }
      }
    }

  def putRequest(command: ActorRef): Route =
    put {
      path("put") {
        parameters('userid, 'gender, 'nickname) {
          (userId, gender, nickName) =>
            val result = eventuateUpdateUserHandler(command, userId, gender, nickName)
            complete(result)
        }
      }
    }

  def postRequest(command: ActorRef): Route =
    post {
      path("post") {
        parameters('userid, 'gender, 'nickname) {
          (userId,gender,nickName) => {
            val result = eventuateCreateUserHandler(command, userId, gender, nickName)
            complete(result)
          }
        }
      }
    }
  def deleteRequest(command: ActorRef): Route =
    delete {
      path("delete") {
        parameters('userid) {
          (userId) =>

            val result = eventuateDeleteHandler(command, userId)
            complete(result)

        }
      }
    }

  def route(command: ActorRef, query: ActorRef): Route = getRequest(query) ~ putRequest(command) ~ postRequest(command) ~ deleteRequest(command)

}
