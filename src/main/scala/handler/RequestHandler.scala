package handler

import Eventuate._
import akka.actor.ActorRef
import akka.util.Timeout
import persistence.User

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

/**
  * Created by knoldus on 5/1/17.
  */
trait RequestHandler {

  implicit val timeout = Timeout(40 seconds)

  import akka.pattern.ask

  def eventuateCreateUserHandler(command: ActorRef, userId: String,gender: String,nickName: String): Future[String] = {
    val createUser = ask(command, CreateAccount(User(userId,gender,nickName)))
    createUser.map { response =>
      response match {
        case  accountCreateSuccess: AccountCreateSuccess  => accountCreateSuccess.msg
        case  accountCreateFailure: AccountCreateFailure => accountCreateFailure.cause.getMessage
      }
    }
  }

  def eventuateUpdateUserHandler(command: ActorRef, userId: String,gender: String,nickName: String): Future[String] = {
    val updateUser = ask(command, UpdateAccount(User(userId,gender,nickName)))
    updateUser.map { response =>
      response match {
        case  accountUpdateSuccess: AccountUpdateSuccess  => accountUpdateSuccess.msg
        case  accountUpdateFailure: AccountUpdateFailure => accountUpdateFailure.cause.getMessage
      }
    }
  }

  def eventuateDeleteHandler(command: ActorRef, userId: String): Future[String] = {
    val deleteUser = ask(command, DeleteAccount(userId))
    deleteUser.map { response =>
      response match {
        case  accountDeleteSuccess: AccountDeleteSuccess  => accountDeleteSuccess.msg
        case  accountDeleteFailure: AccountDeleteFailure => accountDeleteFailure.cause.getMessage
      }
    }
  }

  def getUserHandler(query: ActorRef, userId: String): Future[String] = {
    val getUser = ask(query, ViewAccount(userId))
    getUser.map { response =>
      response match {
        case  viewAccountSuccess: ViewAccountSuccess  => "ViewAccountSuccess" + viewAccountSuccess.userDetail
        case  viewAccountFailure: ViewAccountFailure => "ViewAccountFailure"
      }
    }
  }

}
