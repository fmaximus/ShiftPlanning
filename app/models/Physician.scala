package models
    
import org.joda.time.{LocalDate, Weeks, DateTime}
     
import reactivemongo.bson._
    
case class Physician(fname: String, lname: String, function: Function.Value, _id: Option[BSONObjectID] = None)
case class PhysicianPartTime(timestamp: DateTime, locationId: BSONObjectID, dayOfWeek: WeekDay.Value, off: Boolean)
/*
object Physician {
    implicit object PhysicianBSONReader extends BSONDocumentReader[Physician] {
        def read(doc: BSONDocument): Physician =
            Physician(  
                doc.getAs[String]("fname").get,
                doc.getAs[String]("lname").get,
                doc.getAs[Function.Value]("function").get,
                doc.getAs[BSONObjectID]("_id"))
    }
    
    implicit object PhysicianBSONWriter extends BSONDocumentWriter[Physician] {
        def write(physician: Physician): BSONDocument =
            BSONDocument(
                "_id" -> physician.id.getOrElse(BSONObjectID.generate),
                "fname" -> physician.fname,
                "lname" -> physician.lname,
                "function" -> physician.function)
  }   
}*/