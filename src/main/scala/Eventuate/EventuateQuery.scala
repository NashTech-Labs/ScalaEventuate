package Eventuate

import akka.actor.ActorRef
import com.rbmhtechnology.eventuate.EventsourcedActor
import persistence.{User, cassandraPersistence}

/**
  * Created by knoldus on 5/1/17.
  */

case class ViewAccount(userId: String)

// Replies
case class ViewAccountSuccess(userDetail: User)
case class ViewAccountFailure(cause: Throwable)



class EventuateQuery(replicaId: String, override val eventLog: ActorRef) extends EventsourcedActor with cassandraPersistence{

  override val id = s"s-av-$replicaId"

  /**
    * Command handlers.
    */
  override val onCommand: Receive = {

    case ViewAccount(userId: String) => {
      val result = getUser(userId)
      result match {
            case Some(user) =>  sender() !  ViewAccountSuccess(user)
            case None => sender() ! ViewAccountFailure(new Exception("Unable to get User Details"))
          }
        }
  }

  /**
    * Event handlers.
    */
  override val onEvent: Receive = {

    case _ =>

  }

}
