package models
     
import reactivemongo.bson._
import play.api.libs.json._
import play.modules.reactivemongo.json.BSONFormats._

object EnumUtils {
    def enumFormat[E <: Enumeration](enum: E): Format[E#Value] = new Format[E#Value] {
        def reads(json: JsValue): JsResult[E#Value] = json match {
            case JsString(s) => {
                try {
                    JsSuccess(enum.withName(s))
                } catch {
                    case _: NoSuchElementException => JsError(s"Enumeration expected of type: '${enum.getClass}', but it does not appear to contain the value: '$s'")
                }
            }
            case _ => JsError("String value expected")
        }

        def writes(v: E#Value): JsValue = JsString(v.toString)
    }
    
    def bsonEnumHandler [E <: Enumeration](enum: E): BSONHandler[BSONString, E#Value] = new BSONHandler[BSONString, E#Value] {
        def write(v: E#Value) = BSON.write(v.toString)
        def read(doc: BSONString) = enum.withName(doc.value)
    }
}


object WeekDay extends Enumeration {
    val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
    
    implicit val jsonFormat = EnumUtils.enumFormat(this)
    implicit val bsonHandler = EnumUtils.bsonEnumHandler(this)
}

object AbsenceType extends Enumeration {
    val Holiday, Illness, Scientific = Value
        
    implicit val jsonFormat = EnumUtils.enumFormat(this)
    implicit val bsonHandler = EnumUtils.bsonEnumHandler(this)
}

object Function extends Enumeration {
    val Attending, Resident = Value
        
    implicit val jsonFormat = EnumUtils.enumFormat(this)
    implicit val bsonHandler = EnumUtils.bsonEnumHandler(this)
}