package alex.m.programming.challenge.service

import java.io.{File, FileWriter}

import alex.m.programming.challenge.domain.{Event, Model, ResponseEvent}
import org.slf4j.{Logger, LoggerFactory}

import scala.io.Source
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

class ModelBasedService(private val modelsDirPath: String) extends ProcessEventService {

  val modelsDir = new File(modelsDirPath)

  if (!modelsDir.exists()) {
    throw new RuntimeException("Folder with models not found")
  }

  if (!modelsDir.isDirectory) {
    throw new RuntimeException("Is not a directory")
  }

  private val models = modelsDir.listFiles
    .map { f =>
        val text = Source.fromFile(f).mkString
        Model.fromJson(text)
    }
    .foldLeft(Map[String, Model]()) {
      (map, model) => map + (model.sensorId -> model)
    }

  def processEvent(event: Event): Result = {
    models
      .get(event.sensorId)
      .map { model =>
        println(model)
        val overThreshold = model.threshold > event.value
        val status = if (overThreshold) "NO_ANOMALY" else "ANOMALY"
        Ok(ResponseEvent(event, status))
      }.getOrElse(Ok(ResponseEvent(event)))
  }
}

class MockService extends ProcessEventService {

  def processEvent(event: Event): Result = {
    if (event.sensorId != "invalidTest") {
      Ok(ResponseEvent(event))
    } else {
      Error("Error message!")
    }
  }
}