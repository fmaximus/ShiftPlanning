package models

import org.joda.time.{LocalDate, Weeks, DateTime}

import reactivemongo.bson._
import EnumUtils._

case class Location(name: String, external: Boolean = false, open: Option[Set[WeekDay.Value]],
                    _id: Option[BSONObjectID] = None) extends Entity
