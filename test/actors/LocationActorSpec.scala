package actors

import akka.actor.ActorSystem
import akka.testkit._
import org.scalatest._

class LocationActorSpec
  extends TestKit(ActorSystem("TestKitUsageSpec"))
  with DefaultTimeout with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {



}
