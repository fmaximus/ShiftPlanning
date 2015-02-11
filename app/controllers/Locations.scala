package controllers

import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import scala.concurrent.Future
import reactivemongo.api.Cursor
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import org.slf4j.{LoggerFactory, Logger}
import javax.inject.Singleton
import play.api.mvc._
import play.api.libs.json._

/**
 * The Users controllers encapsulates the Rest endpoints and the interaction with the MongoDB, via ReactiveMongo
 * play plugin. This provides a non-blocking driver for mongoDB as well as some useful additions for handling JSon.
 * @see https://github.com/ReactiveMongo/Play-ReactiveMongo
 */
@Singleton
class Locations extends Controller with MongoController {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Locations])

  /*
   * Get a JSONCollection (a Collection implementation that is designed to work
   * with JsObject, Reads and Writes.)
   * Note that the `collection` is not a `val`, but a `def`. We do _not_ store
   * the collection reference to avoid potential problems in development with
   * Play hot-reloading.
   */
  def collection: JSONCollection = db.collection[JSONCollection]("locations")

  // ------------------------------------------ //
  // Using case classes + Json Writes and Reads //
  // ------------------------------------------ //

  import models._
  import models.JsonFormats._

  def create(id: String) = Action.async(parse.json) {
    request =>
      request.body.validate[Location].map {
        location =>
          collection.insert(location).map {
            lastError =>
              logger.debug(s"Successfully inserted with LastError: $lastError")
              Created(s"Location Created")
          }
      }.recoverTotal{
        e => Future.successful(BadRequest("Detected error:"+ JsError.toFlatJson(e)))
      }
  }

  def update(name: String) = Action.async(parse.json) {
    request =>
      request.body.validate[Location].map {
        location =>
          // find our location by name
          val nameSelector = Json.obj("name" -> name)
          collection.update(nameSelector, location).map {
            lastError =>
              logger.debug(s"Successfully updated with LastError: $lastError")
              Created(s"Location Updated")
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def list = Action.async {
    val cursor: Cursor[Location] = collection.
      find(Json.obj()).
      // sort them by creation date
      sort(Json.obj("created" -> -1)).
      // perform the query and get a cursor of JsObject
      cursor[Location]

    // gather all the JsObjects in a list
    val futureLocationsList: Future[List[Location]] = cursor.collect[List]()

    // transform the list into a JsArray
    val futureLocationsJsonArray: Future[JsArray] = futureLocationsList.map { locations =>
      Json.arr(locations)
    }
    // everything's ok! Let's reply with the array
    futureLocationsJsonArray.map {
      locations =>
        Ok(locations(0))
    }
  }

}