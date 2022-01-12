package io.huta.kafkara.api.routes

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

object Routes {

  def routes(): Route = pathPrefix("v1") {
    pathPrefix("topics") {
      complete("")
    }
  }
}
