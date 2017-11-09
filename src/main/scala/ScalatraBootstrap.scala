import alex.m.programming.challenge._
import org.scalatra._
import javax.servlet.ServletContext

import alex.m.programming.challenge.service.{AvgModelService, LoggerService, ModelBasedService}

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    // val processingService = new LoggerService("logger-file.txt")
    // val processingService = new ModelBasedService("models")
    val processingService = new AvgModelService(3, 26.0)
    context.mount(new AnomalyDetectorServlet(processingService), "/*")
  }
}
