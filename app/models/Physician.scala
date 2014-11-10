package models

import org.virtuslab.unicorn.LongUnicornPlay._
import org.virtuslab.unicorn.LongUnicornPlay.driver.simple._
    
import scala.slick.lifted.Tag
import scala.slick.driver.HsqldbDriver.MappedJdbcType
    
import org.joda.time.{LocalDate, Weeks, DateTime}
import com.github.tototoshi.slick.HsqldbJodaSupport._
    
object Function extends Enumeration {
    val Attending, Resident = Value

    implicit val enumMapper = MappedJdbcType.base[Value, String](_.toString(), this.withName)
}

case class PhysicianId(id: Long) extends AnyVal with BaseId
    
object PhysicianId extends IdCompanion[PhysicianId]
    
case class Physician(fname: String, lname: String, function: Function.Value, id: Option[PhysicianId] = None) extends WithId[PhysicianId]
case class PhysicianPartTime(timestamp: DateTime, locationId: LocationId, dayOfWeek: WeekDay.Value, off: Boolean)

class Physicians(tag: Tag) extends IdTable[PhysicianId, Physician](tag, "physicians") {
  def fname: Column[String] = column[String]("fname")
  def lname: Column[String] = column[String]("lname")
  def function: Column[Function.Value] = column[Function.Value]("function")

  def * = (fname, lname, function, id.?) <> (Physician.tupled, Physician.unapply)
}