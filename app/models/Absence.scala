package models

import org.joda.time.{LocalDate, Weeks, DateTime}
     
import reactivemongo.bson._

case class Education(startDate: LocalDate, endDate: LocalDate, locationId: BSONObjectID, physicianId: BSONObjectID)
case class Absence(startDate: LocalDate, endDate: LocalDate, physicianId: BSONObjectID, absenceType: AbsenceType.Value)
