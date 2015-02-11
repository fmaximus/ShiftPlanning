package models

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
}

