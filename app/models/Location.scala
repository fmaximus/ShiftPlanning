package models

import org.virtuslab.unicorn.LongUnicornPlay._
import org.virtuslab.unicorn.LongUnicornPlay.driver.simple._
    
import scala.slick.lifted.Tag
import scala.slick.driver.HsqldbDriver.MappedJdbcType
    
import org.joda.time.{LocalDate, Weeks, DateTime}
import com.github.tototoshi.slick.HsqldbJodaSupport._
    
case class LocationId(id: Long) extends AnyVal with BaseId

object LocationId extends IdCompanion[LocationId]
    
case class Location(name: String, external: Boolean = false, id: Option[LocationId] = None) extends WithId[LocationId]
case class LocationOpen(timestamp: DateTime, locationId: LocationId, dayOfWeek: WeekDay.Value, open: Boolean)

class Locations(tag: Tag) extends IdTable[LocationId, Location](tag, "locations") {
  def name: Column[String] = column[String]("name")
  def external: Column[Boolean] = column[Boolean]("external")
  
  // Every table needs a * projection with the same type as the table's type parameter
  def * = (name, external, id.?) <> (Location.tupled, Location.unapply)
}