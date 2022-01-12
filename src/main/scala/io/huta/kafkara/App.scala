package io.huta.kafkara

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import com.typesafe.config.ConfigFactory
import io.huta.kafkara.api.routes.Routes
import io.huta.kafkara.user.{UserRegistry, UserRoutes}
import kamon.Kamon
import org.apache.kafka.clients.admin.AdminClient

import java.util.Properties
import scala.concurrent.duration.DurationInt
import scala.util.Failure
import scala.util.Success

object App extends App {

  Kamon.init()

  val rootBehavior = Behaviors.setup[Nothing] { context =>
//    val userRegistryActor = context.spawn(UserRegistry(), "UserRegistryActor")
//    context.watch(userRegistryActor)
//    val userRoutes = new UserRoutes(userRegistryActor)(context.system)

    val routes = Routes.routes()

    startHttpServer(routes)(context.system)

    val root = ConfigFactory.load()
    val bootstrap = root.getConfig("kafka")
    val props = new Properties()
    bootstrap.entrySet().forEach { entry =>
      props.put(entry.getKey, entry.getValue.unwrapped().toString)
    }
    AdminClient.create(props)

    Behaviors.empty
  }

  val system = ActorSystem[Nothing](rootBehavior, "KafkaRestAPI")

  private def startHttpServer(routes: Route)(implicit system: ActorSystem[_]): Unit = {
    import system.executionContext

    Http()
      .newServerAt("localhost", 8080)
      .bind(routes)
      .map(_.addToCoordinatedShutdown(hardTerminationDeadline = 10.seconds))
      .onComplete {
        case Success(binding) =>
          val address = binding.localAddress
          system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
        case Failure(ex) =>
          system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
          system.terminate()
      }
  }
}
