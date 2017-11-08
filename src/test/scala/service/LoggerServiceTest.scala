package service

import java.io.File

import alex.m.programming.challenge.domain.Event
import alex.m.programming.challenge.service.{Error, LoggerService, Ok, Result}
import com.google.gson.Gson
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.io.Source

@RunWith(classOf[JUnitRunner])
class LoggerServiceTest extends FunSuite{

  trait SetUp {
    var file = new File("test-log.txt")
    val service = new LoggerService("test-log.txt")
    val gson = new Gson
    if (file.exists) {
      file.delete
    }
  }

  test("Logger Service with test event"){
    new SetUp {
      val event = Event("eventId", "sensorId", 10000L, 29.99)
      val result: Result = service.processEvent(event)
      assert(result === Ok)
      file = new File("test-log.txt")
      assert(file exists)
      val value: String = Source.fromFile(file).mkString
      val loggedEvent: Event = gson.fromJson(value, classOf[Event])
      assert(loggedEvent === event)
    }
  }

  test("Logger Service with test 3 events"){
    new SetUp {
      val eventSeq = Seq(Event("eventId", "sensorId", 10000L, 29.99),
        Event("eventId2", "sensorId", 10000L, 29.99),
        Event("eventId3", "sensorId", 10000L, 29.99))

      val allOk: Boolean = eventSeq.map(service.processEvent).forall(_ == Ok)
      assert(allOk)
      file = new File("test-log.txt")
      assert(file exists)
      val lines: Iterator[String] = Source.fromFile(file).getLines()

      lines.zipWithIndex.foreach { case (line, idx) =>
        val loggedEvent = gson.fromJson(line, classOf[Event])
        assert(loggedEvent === eventSeq(idx))
      }
    }
  }

  test("Logger Serive should fail" ) {
    val service = new LoggerService(null)
    val event = Event("eventId", "sensorId", 10000L, 29.99)
    val result = service.processEvent(event)
    assert(Error("The file doesn't exists") === result)
  }
}
