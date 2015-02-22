package controllers

import javax.inject.Singleton

import models.Physician
import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.json.Json

@Singleton
class Physicians extends EntityController[Physician] {
  override protected final val logger: Logger = LoggerFactory.getLogger(classOf[Physicians])
  override protected final val entityClass: Class[Physician] = classOf[Physician]
  override protected final val collectionName: String = "physicians"
  override protected final val entityFormat =  models.JsonFormats.physicianFormat
  override protected final val orderBy = Json.obj("function" -> 1, "lname" -> 1, "fname" -> 1)
}