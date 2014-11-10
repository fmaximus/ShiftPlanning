package repositories

import models._

import org.virtuslab.unicorn.LongUnicornPlay._
import org.virtuslab.unicorn.LongUnicornPlay.driver.simple._
    
class LocationsRepository extends BaseIdRepository[LocationId, Location, Locations](TableQuery[Locations])
class PhysiciansRepository extends BaseIdRepository[PhysicianId, Physician, Physicians](TableQuery[Physicians])
class AllocationEventsRepository extends BaseIdRepository[EventId, AllocationEvent, AllocationEvents](TableQuery[AllocationEvents])
class AbsenceEventsRepository extends BaseIdRepository[EventId, AbsenceEvent, AbsenceEvents](TableQuery[AbsenceEvents])