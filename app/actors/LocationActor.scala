package actors
    
import akka.actor.Actor
import repositories.LocationsRepository
import models.{Location, LocationId}
    
class LocationActor extends RepositoryActor[LocationId, Location, LocationsRepository] with RepositoryMessages[LocationId, Location]  {
    override val repository = new LocationsRepository
}   