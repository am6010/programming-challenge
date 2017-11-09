package alex.m.programming.challenge.service

import java.io.FileWriter

import alex.m.programming.challenge.domain.{Event, ResponseEvent}
import org.slf4j.{Logger, LoggerFactory}

import scala.util.Try

trait Result
case class Ok(responseEvent: ResponseEvent) extends Result
case class Error(message: String) extends Result

trait ProcessEventService {
  def processEvent(event: Event): Result
}

class LoggerService(private val filePath: String) extends ProcessEventService {

  private val logger: Logger =  LoggerFactory.getLogger(getClass)

  def processEvent(event: Event): Result = {
    logger.info(event.toString)
    val writerOpt = Try(new FileWriter(filePath, true)).toOption
    val eventString = Event.toJson(event)

    writerOpt.map {writer =>
      writer.write(eventString + "\n")
      writer.close()
      Ok(ResponseEvent(event))
    }.getOrElse(Error("The file doesn't exists"))
  }
}

class MockService extends ProcessEventService {

  def processEvent(event: Event): Result = {
    Ok(ResponseEvent(event))
  }
}