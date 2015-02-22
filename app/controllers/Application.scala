package controllers

import javax.inject.{Singleton, Inject}
import org.slf4j.{LoggerFactory, Logger}
import play.api.mvc._
import play.api.Routes

@Singleton
class Application extends Controller {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Application])

  def index(any: String) = Action {
    logger.info("Serving index page...")
    Ok(views.html.index())
  }

  def javascriptRoutes = Action { implicit request =>
    import routes.javascript._
    Ok(
      Routes.javascriptRouter("jsRoutes")(
        Locations.list,
        Physicians.list
      )
    ).as("text/javascript")
  }                 

}