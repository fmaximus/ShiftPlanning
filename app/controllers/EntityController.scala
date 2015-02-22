package controllers

import akka.actor.Status.Failure
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.Cursor
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import org.slf4j.{LoggerFactory, Logger}
import play.api.mvc._
import play.api.libs.json._
import reactivemongo.core.commands.LastError

import scala.concurrent.Future
import scala.util.Success

/**
 * The Entity controllers encapsulates the Rest endpoints and the interaction with the MongoDB, via ReactiveMongo
 * play plugin. This provides a non-blocking driver for mongoDB as well as some useful additions for handling JSon.
 * @see https://github.com/ReactiveMongo/Play-ReactiveMongo
 */
trait EntityController[T] extends Controller with MongoController {
  protected def logger: Logger
  protected def collectionName: String
  protected def entityClass: Class[T]
  protected implicit def entityFormat: Format[T]
  protected def orderBy = Json.obj("_id" -> 1)

  /*
 * Get a JSONCollection (a Collection implementation that is designed to work
 * with JsObject, Reads and Writes.)
 * Note that the `collection` is not a `val`, but a `def`. We do _not_ store
 * the collection reference to avoid potential problems in development with
 * Play hot-reloading.
 */
  protected def collection: JSONCollection = db.collection[JSONCollection](collectionName)

  // ------------------------------------------ //
  // Using case classes + Json Writes and Reads //
  // ------------------------------------------ //

  import models._
  import models.JsonFormats._

  def save = Action.async(parse.json) {
    request =>
      request.body.validate[T].map {
        entity =>
          collection.save(entity).map {
            lastError =>
              logger.debug(s"Successfully inserted with LastError: $lastError")
              Ok(Json.toJson(entity))
          }
      }.recoverTotal{
        e => Future.successful(BadRequest("Detected error:"+ JsError.toFlatJson(e)))
      }
  }

  def get(id: String) = Action.async {
    val futureEntity : Future[Option[T]] = collection.
      find(Json.obj("_id" -> Json.obj("$oid" -> id))).
      one[T]

    futureEntity.map { maybeEntity =>
      maybeEntity.map { entity => Json.toJson(entity) }.map {  entity => Ok(entity) }.getOrElse(NotFound)
    }
  }

  def delete(id: String) = Action.async {
    val futureError : Future[LastError] = collection.remove(Json.obj("_id" -> Json.obj("$oid" -> id)))

    futureError.map { lastError =>
      if (lastError.ok) Ok else NotFound(lastError.errMsg)
    }
  }

  def list = Action.async {
    val cursor: Cursor[T] = collection.
      find(Json.obj()).
      sort(orderBy).
      cursor[T]

    val futureMaybeEntitiesList: Future[List[T]] = cursor.collect[List]()

    for {
      entities <- futureMaybeEntitiesList
    } yield Ok(Json.toJson(entities))
  }


}
