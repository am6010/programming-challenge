package service

import alex.m.programming.challenge.domain.{Event, ResponseEvent}
import alex.m.programming.challenge.service.{ModelBasedService, Ok}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ModelBasedServiceTest extends FunSuite {

  test("an null file path should return an error") {
    assertThrows[RuntimeException] {
      new ModelBasedService(null)
    }
  }

  test("an invalid file path should return an error") {
    assertThrows[RuntimeException] {
      new ModelBasedService("model-invalid")
    }
  }

  test("with a valid folder in resources with no anomaly") {
    val service = new ModelBasedService("models/")
    val event = Event(
     "cj86g5ypk000004zvevipqxfn",
      "fd0a635d-2aaf-4460-a817-6353e1b6c050",
      1506723249,
      25.6734)
    val response = service.processEvent(event)
    assert(response === Ok(ResponseEvent(event, "NO_ANOMALY")))
  }

  test("with a valid folder in resources with anomaly") {
    val service = new ModelBasedService("models/")
    val event = Event(
      "cj86g5ypk000004zvevipqxfn",
      "fd0a635d-2aaf-4460-a817-6353e1b6c050",
      1506723249,
      35.6734)
    val response = service.processEvent(event)
    assert(response === Ok(ResponseEvent(event, "ANOMALY")))
  }

  test("with a valid folder but no model found") {
    val service = new ModelBasedService("models/")
    val event = Event(
      "cj86g5ypk000004zvevipqxfn",
      "fd0a635d-2aaf-4460-a817-6353e1b6c05X",
      1506723249,
      35.6734)
    val response = service.processEvent(event)
    assert(response === Ok(ResponseEvent(event)))
  }
}
