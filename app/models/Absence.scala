package models

import org.joda.time.{LocalDate, Weeks, DateTime}
     
import reactivemongo.bson._

case class Education(timestamp: DateTime, startDate: LocalDate, endDate: LocalDate, locationId: BSONObjectID, physicianId: BSONObjectID)
case class Absence(timestamp: DateTime, startDate: LocalDate, endDate: LocalDate, physicianId: BSONObjectID, absenceType: AbsenceType.Value)

