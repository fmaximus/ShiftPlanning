package models

import org.joda.time.DateTime
import reactivemongo.bson.BSONObjectID

trait Entity

object JsonFormats {
  import play.api.libs.json.Json
  import play.api.data._
  import play.api.data.Forms._
  import play.modules.reactivemongo.json.BSONFormats._
  import reactivemongo.bson._
  import EnumUtils._

  // Generates Writes and Reads for Feed and User thanks to Json Macros
  implicit val locationFormat = Json.format[Location]
  implicit val physicianFormat = Json.format[Physician]

  implicit val eductionFormat = Json.format[Education]
  implicit val absenceFormat = Json.format[Absence]
}

