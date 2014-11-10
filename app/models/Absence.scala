package models

import org.virtuslab.unicorn.LongUnicornPlay._
import org.virtuslab.unicorn.LongUnicornPlay.driver.simple._

import org.joda.time.{LocalDate, Weeks, DateTime}
import com.github.tototoshi.slick.HsqldbJodaSupport._

import scala.slick.lifted.{ProvenShape, ForeignKeyQuery, Tag}
import scala.slick.driver.HsqldbDriver.MappedJdbcType
    
object WeekDay extends Enumeration {
  val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value

  implicit val enumMapper = MappedJdbcType.base[Value, Int](_.id, this.apply)
}

object AbsenceType extends Enumeration {
    val Holiday, Illness, Scientific = Value

    implicit val enumMapper = MappedJdbcType.base[Value, Int](_.id, this.apply)
}

case class Education(timestamp: DateTime, startDate: LocalDate, endDate: LocalDate, locationId: LocationId, physicianId: PhysicianId)
case class Absence(timestamp: DateTime, startDate: LocalDate, endDate: LocalDate, physicianId: PhysicianId, absenceType: AbsenceType.Value)

class EducationSchedule(tag: Tag) extends Table[Education](tag, "education_schedule") {
  def timestamp: Column[DateTime] = column[DateTime]("TS", O.PrimaryKey)
  def startDate: Column[LocalDate] = column[LocalDate]("start_date")
  def endDate: Column[LocalDate] = column[LocalDate]("end_date")
  def locationId: Column[LocationId] = column[LocationId]("location_id", O.Nullable)
  def physicianId: Column[PhysicianId] = column[PhysicianId]("physician_id")

  def * = (timestamp, startDate, endDate, locationId, physicianId) <> (Education.tupled, Education.unapply)

  def location: ForeignKeyQuery[Locations, Location] = foreignKey("EDU_LOC_FK", locationId, TableQuery[Locations])(_.id)
  def physician: ForeignKeyQuery[Physicians, Physician] = foreignKey("EDU_PHY_FK", physicianId, TableQuery[Physicians])(_.id)
}

class Absences(tag: Tag) extends Table[Absence](tag, "absences") {
  import AbsenceType.{Value => Type}

  def timestamp: Column[DateTime] = column[DateTime]("ts", O.PrimaryKey)
  def startDate: Column[LocalDate] = column[LocalDate]("start_date")
  def endDate: Column[LocalDate] = column[LocalDate]("end_date")
  def physicianId: Column[PhysicianId] = column[PhysicianId]("physician_id")
  def absenceType: Column[Type] = column[Type]("absence_type")

  def * = (timestamp, startDate, endDate, physicianId, absenceType) <> (Absence.tupled, Absence.unapply)

  def physician: ForeignKeyQuery[Physicians, Physician] = foreignKey("EDU_PHY_FK", physicianId, TableQuery[Physicians])(_.id)
}

