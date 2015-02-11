package models

import org.joda.time.{LocalDate, Weeks, DateTime}
     
import reactivemongo.bson._

case class AllocationEvent(date: LocalDate, locationId: BSONObjectID, physicianId: BSONObjectID, id: Option[BSONObjectID] = None)
case class AbsenceEvent(date: LocalDate, physicianId: BSONObjectID, absenceType: AbsenceType.Value, id: Option[BSONObjectID] = None)
