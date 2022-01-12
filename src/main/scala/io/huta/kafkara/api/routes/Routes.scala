package io.huta.kafkara.api.routes

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import io.huta.kafkara.user.UserRoutes

object Routes {

  def routes(userRoutes: UserRoutes): Route = pathPrefix("v1") {
    userRoutes.userRoutes
  }
}
