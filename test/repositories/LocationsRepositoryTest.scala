package repositories



class LocationsRepositoryTest extends  {

  "Users repository" should "save and query users" in rollback { implicit session =>
    // setup
    val repository = new LocationRepository
    repository.create

    val location = Location("Room 1")
    val locationId = repository save location
    val locationOpt = repository findById locationId

    locationOpt.map(_.name) shouldEqual Some(location.name)
    locationOpt.flatMap(_.id) shouldNot be(None)
  }
}