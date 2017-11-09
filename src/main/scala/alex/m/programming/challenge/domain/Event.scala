package alex.m.programming.challenge.domain

import com.google.gson.Gson

import scala.reflect.ClassTag
import scala.reflect._

case class Event(eventId: String, sensorId: String, timestamp: Long, value: Double)

case class ResponseEvent(eventId: String,
                         sensorId: String,
                         timestamp: Long,
                         value: Double,
                         status: String = "NO_MODEL",
                         cause: String = "",
                         message: String = "")

trait gsonHelper[T] {
  private val gson = new Gson

  def toJson(t: T) : String = {
    gson.toJson(t)
  }

  def fromJson(json: String)(implicit tag: ClassTag[T]): T = {
    gson.fromJson(json, tag.runtimeClass)
  }
}

object ResponseEvent extends gsonHelper[ResponseEvent] {
  def apply(event: Event): ResponseEvent =
    new ResponseEvent(event.eventId, event.sensorId, event.timestamp, event.value)

  def apply(event: Event, status: String): ResponseEvent =
    new ResponseEvent(event.eventId, event.sensorId, event.timestamp, event.value, status)
}

object Event extends gsonHelper[Event]{
}

case class Model(sensorId: String, model: String, threshold: Double)

object Model extends gsonHelper[Model]