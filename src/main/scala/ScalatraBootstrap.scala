import alex.m.programming.challenge._
import org.scalatra._
import javax.servlet.ServletContext

import alex.m.programming.challenge.service.LoggerService

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    val processingService = new LoggerService("logger-file.txt")
    context.mount(new AnomalyDetectorServlet(processingService), "/*")
  }
}
