package alex.m.programming.challenge

import alex.m.programming.challenge.service.MockService
import org.scalatra.test.scalatest._
import org.scalatest.FunSuiteLike

class AnomalyDetectorServletTests extends ScalatraSuite with FunSuiteLike {

  addServlet(new AnomalyDetectorServlet(new MockService), "/*")

  test("POST/api/event on AnomalyDetectorServlet should return status 200") {
    val body = "{\"eventId\" : \"cj86g5ypk000004zvevipqxfn\", " +
      "\"sensorId\" : \"fd0a635d-2aaf-4460-a817-6353e1b6c050\"," +
      " \"timestamp\" : \"1506753249\"," +
      "\"value\" : \"27.6734\"}"
    post("/api/event", body) {
      status should equal (200)
    }
  }

  test("POST/api/event server error 500") {
    val body = "{\"eventId\" : \"cj86g5ypk000004zvevipqxfn\", " +
      "\"sensorId\" : \"invalidTest\"," +
      " \"timestamp\" : \"1506753249\"," +
      "\"value\" : \"27.6734\"}"
    post("/api/event", body) {
      status should equal (500)
    }
  }

}
