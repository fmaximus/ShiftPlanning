package actors

import org.virtuslab.unicorn.LongUnicornPlay._
import org.virtuslab.unicorn.LongUnicornPlay.driver.simple._
import play.api.Play.current
import akka.actor.Actor
import scala.reflect._
import scala.reflect.runtime.universe._

trait RepositoryMessages[Id <: BaseId, Entity <: WithId[Id]] {
  sealed trait RepositoryMessage

  case class Create(entity: Entity)  extends RepositoryMessage
  case class Update(lentity: Entity) extends RepositoryMessage
  case class Fetch(id: Id)           extends RepositoryMessage
  case class Delete(id: Id)          extends RepositoryMessage

  case object FetchAll    extends RepositoryMessage
  case object DeleteAll   extends RepositoryMessage
  case object GetIds      extends RepositoryMessage
  case object CreateTable extends RepositoryMessage
  case object DropTable   extends RepositoryMessage

  val ct = classTag[RepositoryMessage]

}

abstract class RepositoryActor[Id <: BaseId : ClassTag, Entity <: WithId[Id] : ClassTag, Repository <: BaseIdRepository[Id, Entity, Table] forSome { type Table <: IdTable[Id, Entity] } ] extends Actor with RepositoryMessages[Id, Entity] {
  implicit val repository: Repository
  def receive: Receive = {

    case message if ct.runtimeClass.isInstance(message)  => play.api.db.slick.DB.withTransaction { implicit session => message match {
      case Create(entity: Entity) => sender ! repository.save(entity)
      case Update(entity: Entity) => sender ! repository.save(entity)
      case Fetch(id: Id)          => sender ! repository.findById(id)
      case Delete(id: Id)         => sender ! repository.deleteById(id)
      case FetchAll               => sender ! repository.findAll
      case DeleteAll              => sender ! repository.deleteAll
      case GetIds                 => sender ! repository.allIds
      case CreateTable            => sender ! repository.create
      case DropTable              => sender ! repository.drop
    }
    }
  }
}