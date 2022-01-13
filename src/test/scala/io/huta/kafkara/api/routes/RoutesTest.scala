package io.huta.kafkara.api.routes

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.huta.kafkara.config.KafkaClientBootstrap
import io.huta.kafkara.topic.TopicInfoProvider
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class RoutesTest extends AnyWordSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  lazy val testKit: ActorTestKit = ActorTestKit()

  implicit def typedSystem: ActorSystem[Nothing] = testKit.system

  override def createActorSystem(): akka.actor.ActorSystem = testKit.system.classicSystem

//  val routes = new Routes().routes()

  "routes" should {
    "return 200" in {
      val request = HttpRequest(uri = "/v1/topics")

//      request ~> routes ~> check {
//        status should ===(StatusCodes.OK)
//      }
    }
  }
}
