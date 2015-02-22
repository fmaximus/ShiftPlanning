package models
    
import org.joda.time.{LocalDate, Weeks, DateTime}
     
import reactivemongo.bson._
    
case class Physician(fname: String, lname: String, function: Function.Value, partTime: Option[Set[WeekDay.Value]],
                     _id: Option[BSONObjectID] = None) extends Entity
