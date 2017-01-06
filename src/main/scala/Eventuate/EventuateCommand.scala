package Eventuate

import akka.actor.ActorRef
import com.rbmhtechnology.eventuate.EventsourcedActor
import persistence.{User, cassandraPersistence}

import scala.util.{Failure, Success}

/**
  * Created by knoldus on 5/1/17.
  */


//Commands

case class CreateAccount(userDetail: User)
case class UpdateAccount(userDetail: User)
case class DeleteAccount(useId: String)



// Events

case class Created(userDetail: User)
case class Updated(userDetail: User)
case class Deleted(useId: String)


//Replies

case class AccountCreateFailure(cause: Throwable)
case class AccountUpdateFailure(cause: Throwable)
case class AccountDeleteFailure(cause: Throwable)

case class AccountCreateSuccess(msg: String)
case class AccountUpdateSuccess(msg: String)
case class AccountDeleteSuccess(msg: String)

class EventuateCommand(replicaId: String, override val eventLog: ActorRef) extends EventsourcedActor with cassandraPersistence{

  override val id = s"s-av-$replicaId"
  /**
    * Command handlers.
    */
  override val onCommand: Receive = {

    case CreateAccount(userDetail: User) => {
      persist(Created(userDetail)) {
        case Success(usr) => {
          val createAccount = insertUser(userDetail)
          createAccount match {
            case true =>
              sender() ! AccountCreateSuccess("Accounts Created Successfully")
            case false => sender() ! AccountCreateFailure(new Exception("Unable to create Account"))
          }
        }
        case Failure(err) =>
          sender() ! AccountCreateFailure(err)
      }
    }

    case UpdateAccount(userDetail: User) => {
      persist(Updated(userDetail)) {
        case Success(usr) => {
          val createAccount = updateUser(userDetail)
          match {
            case true => sender() ! AccountUpdateSuccess("Accounts Update Successfully")
            case false => sender() ! AccountUpdateFailure(new Exception("Unable  to update Account"))
          }
        }
        case Failure(err) =>
          sender() ! AccountUpdateFailure(err)
      }
    }

    case DeleteAccount(userId: String) => {
      persist(Deleted(userId)) {
        case Success(usr) => {
          val createAccount = deleteUser(userId)
          createAccount match {
            case true => sender() ! AccountDeleteSuccess("Accounts Delete Successfully")
            case false => sender() ! AccountDeleteFailure( new Exception("Unable to Delete Account"))
          }
        }
        case Failure(err) =>
          sender() ! AccountDeleteFailure( err)
      }
    }
  }




  /**
    * Event handlers.
    */
  override val onEvent: Receive = {

    case e: Created => insertUser(e.userDetail)

    case _ =>

  }


}
