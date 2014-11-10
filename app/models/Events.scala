package models

import org.virtuslab.unicorn.LongUnicornPlay._
import org.virtuslab.unicorn.LongUnicornPlay.driver.simple._
    
import scala.slick.lifted.{ForeignKeyQuery, Tag}
    
import org.joda.time.{LocalDate, Weeks, DateTime}
import com.github.tototoshi.slick.HsqldbJodaSupport._    
    
case class EventId(id: Long) extends AnyVal with BaseId
object EventId extends IdCompanion[EventId]
    
case class AllocationEvent(date: LocalDate, locationId: LocationId, physicianId: PhysicianId, id: Option[EventId] = None) extends WithId[EventId]
case class AbsenceEvent(date: LocalDate, physicianId: PhysicianId, absenceType: AbsenceType.Value, id: Option[EventId] = None) extends WithId[EventId]
    
class AllocationEvents(tag: Tag) extends IdTable[EventId, AllocationEvent](tag, "allocation_events") {
  def date: Column[LocalDate] = column[LocalDate]("date")
  def locationId: Column[LocationId] = column[LocationId]("location_id", O.Nullable)
  def physicianId: Column[PhysicianId] = column[PhysicianId]("physician_id")

  def * = (date, locationId, physicianId, id.?) <> (AllocationEvent.tupled, AllocationEvent.unapply)

  def location: ForeignKeyQuery[Locations, Location] = foreignKey("ALL_LOC_FK", locationId, TableQuery[Locations])(_.id)
  def physician: ForeignKeyQuery[Physicians, Physician] = foreignKey("ALL_PHY_FK", physicianId, TableQuery[Physicians])(_.id)
}

class AbsenceEvents(tag: Tag) extends IdTable[EventId, AbsenceEvent](tag, "absence_events") {
  import AbsenceType.{Value => Type}

  def date: Column[LocalDate] = column[LocalDate]("date")
  def physicianId: Column[PhysicianId] = column[PhysicianId]("physician_id")
  def absenceType: Column[Type] = column[Type]("absence_type")
      
  def * = (date, physicianId, absenceType, id.?) <> (AbsenceEvent.tupled, AbsenceEvent.unapply)

  def physician: ForeignKeyQuery[Physicians, Physician] = foreignKey("ALL_PHY_FK", physicianId, TableQuery[Physicians])(_.id)
}