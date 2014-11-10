import models._
import models.Function._

import scala.slick.driver.HsqldbDriver.simple._

// The main application
object HelloSlick extends App {

  val locations: TableQuery[Locations] = TableQuery[Locations]
  val doctors: TableQuery[Physicians] = TableQuery[Physicians]

  // Create a connection (called a "session") to an in-memory H2 database
  val db = Database.forURL("jdbc:hsqldb:mem:hello", driver = "org.hsqldb.jdbc.JDBCDriver")
  db.withSession { implicit session =>

    // Create the schema by combining the DDLs for the Suppliers and Coffees
    // tables using the query interfaces
    (locations.ddl ++ doctors.ddl).create


    locations ++= Seq (
      Location("Room 1: Abdominal surgery"),
      Location("Room 2: Ear-nose-throat surgery"),
      Location("Room 3: Neurosurgery"),
      Location("Room 4: Maxillofacial surgery"),
      Location("Room 5: Gynaecology"),
      Location("Room 6: Urology"),
      Location("Room 7: Cardiac surgery"),
      Location("Room 8: Cardiac surgery"),
      Location("Room 9: Orthopedic surgery"),
      Location("Room 10: Orthopedic surgery"),
      Location("Room 11: Thorax surgery"),
      Location("Room 12: Hepatobiliary surgery"),
      Location("Room 14: Day surgery"),
      Location("Room 15: Ear-nose-throat surgery"),
      Location("Room 16: Oftalmology"),
      Location("Room 18: Day surgery"),
      Location("Catherisation lab 4"),
      Location("Catherisation lab 6"),
      Location("Angiography 1"),
      Location("Angiography 2"),
      Location("Fertility"),
      Location("RX"),
      Location("MRI"),
      Location("CT"),
      Location("Pain clinic"),
      Location("Consultation 1"),
      Location("Consultation 2"),
      Location("Preparation box"),
      Location("Recovery"),
      Location("Emergency medicine", true),
      Location("Intensive care", true),
      Location("Hospital 1: Duffel", true),
      Location("Hospital 2: OLV Aalst", true),
      Location("Hospital 3: AZ Middelheim Wilrijk", true)
    )
    // Insert some coffees (using JDBC's batch insert feature)
    doctors ++= Seq (
      Physician("Vera", "Saldien", Attending),
      Physician("Sabine", "Maes", Attending),
      Physician("Nathalie", "Bosserez", Attending),
      Physician("Hilde", "Coppejans", Attending),
      Physician("Joris", "Depuydt", Attending),
      Physician("Pieter", "Mertens", Attending),
      Physician("Heleen", "Apostel", Resident),
      Physician("Michiel", "Baeten", Resident),
      Physician("Joke", "De Buck", Resident),
      Physician("Anja", "De Meyer", Resident),
      Physician("Laura", "Deighton", Resident),
      Physician("Valerie", "Gelijkens", Resident)
    )

    /* Read / Query / Select */
  
    // Print the SQL for the Coffees query
    println("Generated SQL for base Coffees query:\n" + locations.selectStatement)

    // Query the Coffees table using a foreach and print each row
    locations foreach { case Location(name, external, id) => println("* " + name + (if(external) " (external)" else "")) }
    doctors filter(_.function === Attending) foreach { case Physician(fname, lname, function, id) => println(s"* $fname $lname $function") }

    /* Filtering / Where */

    // Construct a query where the price of Coffees is > 9.0
    val filterQuery = locations.filter(_.external)

    println("Generated SQL for filter query:\n" + filterQuery.selectStatement)

    // Execute the query
    println(filterQuery.list)
  

    /* Update *
  
    // Construct an update query with the sales column being the one to update
    val updateQuery: Query[Column[Int], Int, Seq] = coffees.map(_.sales)

    // Print the SQL for the Coffees update query
    println("Generated SQL for Coffees update:\n" + updateQuery.updateStatement)
  
    // Perform the update
    val numUpdatedRows = updateQuery.update(1)
  
    println(s"Updated $numUpdatedRows rows")


    /* Delete */

    // Construct a delete query that deletes coffees with a price less than 8.0
    val deleteQuery: Query[Coffees,(String, Int, Double, Int, Int), Seq] =
      coffees.filter(_.price < 8.0)

    // Print the SQL for the Coffees delete query
    println("Generated SQL for Coffees delete:\n" + deleteQuery.deleteStatement)

    // Perform the delete
    val numDeletedRows = deleteQuery.delete

    println(s"Deleted $numDeletedRows rows")
  
  
    /* Selecting Specific Columns */
  
    // Construct a new coffees query that just selects the name
    val justNameQuery: Query[Column[String], String, Seq] = coffees.map(_.name)
  
    println("Generated SQL for query returning just the name:\n" +
      justNameQuery.selectStatement)
  
    // Execute the query
    println(justNameQuery.list)
  
  
    /* Sorting / Order By */
  
    val sortByPriceQuery: Query[Coffees, (String, Int, Double, Int, Int), Seq] =
      coffees.sortBy(_.price)
  
    println("Generated SQL for query sorted by price:\n" +
      sortByPriceQuery.selectStatement)
  
    // Execute the query
    println(sortByPriceQuery.list)
  
  
    /* Query Composition */
  
    val composedQuery: Query[Column[String], String, Seq] =
      coffees.sortBy(_.name).take(3).filter(_.price > 9.0).map(_.name)
  
    println("Generated SQL for composed query:\n" +
      composedQuery.selectStatement)
  
    // Execute the composed query
    println(composedQuery.list)
  
  
    /* Joins */
  
    // Join the tables using the relationship defined in the Coffees table
    val joinQuery: Query[(Column[String], Column[String]), (String, String), Seq] = for {
      c <- coffees if c.price > 9.0
      s <- c.supplier
    } yield (c.name, s.name)

    println("Generated SQL for the join query:\n" + joinQuery.selectStatement)

    // Print the rows which contain the coffee name and the supplier name
    println(joinQuery.list)
    
    
    /* Computed Values */
    
    // Create a new computed column that calculates the max price
    val maxPriceColumn: Column[Option[Double]] = coffees.map(_.price).max
    
    println("Generated SQL for max price column:\n" + maxPriceColumn.selectStatement)
    
    // Execute the computed value query
    println(maxPriceColumn.run)
    
    
    /* Manual SQL / String Interpolation */

    // Required import for the sql interpolator
    import scala.slick.jdbc.StaticQuery.interpolation
  
    // A value to insert into the statement
    val state = "CA"
  
    // Construct a SQL statement manually with an interpolated value
    val plainQuery = sql"select SUP_NAME from SUPPLIERS where STATE = $state".as[String]
    
    println("Generated SQL for plain query:\n" + plainQuery.getStatement)
    
    // Execute the query
    println(plainQuery.list)
    */
  }
}
