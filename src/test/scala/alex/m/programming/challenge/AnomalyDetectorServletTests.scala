package alex.m.programming.challenge

import org.scalatra.test.scalatest._
import org.scalatest.FunSuiteLike

class AnomalyDetectorServletTests extends ScalatraSuite with FunSuiteLike {

  addServlet(classOf[AnomalyDetectorServlet], "/*")

  test("GET / on AnomalyDetectorServlet should return status 200"){
    get("/"){
      status should equal (200)
    }
  }

}
