package io.huta.kafkara.api.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import io.huta.kafkara.topic.TopicInfoProvider
import io.huta.kafkara.utils.Logging

import scala.util.{Failure, Success}

class Routes(topicInfo: TopicInfoProvider) extends Logging {
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import io.huta.kafkara.api.model.JsonFormats._

  def routes(): Route = pathPrefix("v1") {
    pathPrefix("topics") {
      onComplete(topicInfo.listTopics()) {
        case Success(value) => complete(StatusCodes.OK, value)
        case Failure(exception) =>
          extractUri { uri =>
            log.error(exception)(uri.toString())
            complete(StatusCodes.InternalServerError, exception.getMessage)
          }
      }
    } ~
    pathPrefix("consumers") {
      onComplete(topicInfo.listConsumers()) {
        case Success(value) => complete(StatusCodes.OK, value)
        case Failure(exception) =>
          extractUri { uri =>
            log.error(exception)(uri.toString())
            complete(StatusCodes.InternalServerError, exception.getMessage)
          }
      }
    }
  }
}
