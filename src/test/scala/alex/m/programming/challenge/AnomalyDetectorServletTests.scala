package alex.m.programming.challenge

import alex.m.programming.challenge.service.MockService
import org.scalatra.test.scalatest._
import org.scalatest.FunSuiteLike

class AnomalyDetectorServletTests extends ScalatraSuite with FunSuiteLike {

  addServlet(new AnomalyDetectorServlet(new MockService), "/*")

  test("GET / on AnomalyDetectorServlet should return status 200"){
    get("/"){
      status should equal (200)
    }
  }

}
