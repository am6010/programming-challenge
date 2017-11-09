package alex.m.programming.challenge

import alex.m.programming.challenge.domain.{Event, ResponseEvent}
import alex.m.programming.challenge.service.{Error, Ok, ProcessEventService}
import org.scalatra._
import org.slf4j.{Logger, LoggerFactory}


class AnomalyDetectorServlet(private val eventProcessingService: ProcessEventService) extends ScalatraServlet {

  private val logger: Logger =  LoggerFactory.getLogger(getClass)

  post("/api/event") {
    val body = request.body
    val event = Event.fromJson(body)
    val result = eventProcessingService.processEvent(event)
    result match {
      case Error(msg) => InternalServerError(msg)
      case Ok(responseEvent) => ResponseEvent.toJson(responseEvent)
    }
  }
}
