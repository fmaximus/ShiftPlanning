package models
    
import org.joda.time.{LocalDate, Weeks, DateTime}
       
import reactivemongo.bson._
import EnumUtils._

case class Location(name: String, external: Boolean = false, id: Option[BSONObjectID] = None)
case class LocationOpen(timestamp: DateTime, locationId: BSONObjectID, dayOfWeek: WeekDay.Value, open: Boolean, id: Option[BSONObjectID] = None)
/*
object Location {
    implicit object LocationBSONReader extends BSONDocumentReader[Location] {
        def read(doc: BSONDocument): Location =
            Location(  
                doc.getAs[String]("name").get,
                doc.getAs[Boolean]("external").getOrElse(false),
                doc.getAs[BSONObjectID]("_id"))
    }
    
    implicit object LocationBSONWriter extends BSONDocumentWriter[Location] {
        def write(location: Location): BSONDocument =
            BSONDocument(
                "_id" -> location.id.getOrElse(BSONObjectID.generate),
                "name" -> location.name,
                "external" -> location.external)
  }   
}*/