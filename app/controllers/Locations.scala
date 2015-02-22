package controllers

import models.Location
import org.slf4j.{LoggerFactory, Logger}
import javax.inject.Singleton


/**
 * The Users controllers encapsulates the Rest endpoints and the interaction with the MongoDB, via ReactiveMongo
 * play plugin. This provides a non-blocking driver for mongoDB as well as some useful additions for handling JSon.
 * @see https://github.com/ReactiveMongo/Play-ReactiveMongo
 */
@Singleton
class Locations extends EntityController[Location] {
  override protected final val logger: Logger = LoggerFactory.getLogger(classOf[Locations])
  override protected final val entityClass: Class[Location] = classOf[Location]
  override protected final val collectionName: String = "locations"
  override protected final val entityFormat =  models.JsonFormats.locationFormat
}