package io.huta.kafkara

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import io.huta.kafkara.api.routes.Routes
import io.huta.kafkara.config.KafkaClientBootstrap
import io.huta.kafkara.topic.TopicInfoProvider
import io.huta.kafkara.user.{UserRegistry, UserRoutes}
import io.huta.kafkara.utils.Logging
import kamon.Kamon
import org.apache.kafka.clients.admin.AdminClient

import java.util.Properties
import java.util.concurrent.TimeUnit
import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.{DurationInt, FiniteDuration}
import scala.util.Failure
import scala.util.Success

object App extends Logging {

  def main(args: Array[String]): Unit = {
    Kamon.init()

    val rootBehavior = Behaviors.setup[Nothing] { context =>
      //    val userRegistryActor = context.spawn(UserRegistry(), "UserRegistryActor")
      //    context.watch(userRegistryActor)
      //    val userRoutes = new UserRoutes(userRegistryActor)(context.system)
      val root = ConfigFactory.load()
      val akkaHTTPServer = root.getConfig("akka.http.server")
      implicit val timeout: Timeout = Timeout(
        FiniteDuration(
          akkaHTTPServer.getDuration("request-timeout").toMillis,
          TimeUnit.MILLISECONDS
        )
      )
      implicit val executionContext: ExecutionContextExecutor = context.executionContext

      val kafkaClient = KafkaClientBootstrap.kafkaClient(root)
      val infoProvider = new TopicInfoProvider(kafkaClient)
      val routes = new Routes(infoProvider).routes()

      startHttpServer(routes)(context.system)

      Behaviors.empty
    }

    val system = ActorSystem[Nothing](rootBehavior, "KafkaRestAPI")
  }

  private def startHttpServer(routes: Route)(implicit system: ActorSystem[_]): Unit = {
    import system.executionContext

    Http()
      .newServerAt("localhost", 8080)
      .bind(routes)
      .map(_.addToCoordinatedShutdown(hardTerminationDeadline = 10.seconds))
      .onComplete {
        case Success(binding) =>
          val address = binding.localAddress
          log.info(s"Server online at http://${address.getHostString}:${address.getPort}/")
        case Failure(ex) =>
          log.error(ex)("Failed to bind HTTP endpoint, terminating system")
          system.terminate()
      }
  }
}
