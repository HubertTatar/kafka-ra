lazy val akkaHttpVersion  = "10.2.7"
lazy val akkaVersion      = "2.6.18"
lazy val kafkaVersion     = "3.0.0"
lazy val kamonVersion     = "2.4.1"

// Run in a separate JVM, to make sure sbt waits until all threads have
// finished before returning.
// If you want to keep the application running while executing other
// sbt tasks, consider https://github.com/spray/sbt-revolver/
fork := true

lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      organization    := "io.huta",
      scalaVersion    := "2.13.4"
    )),
    name := "kafka-ra",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
      "org.log4s"         %% "log4s"                    % "1.10.0",
      "ch.qos.logback"    % "logback-classic"           % "1.2.10",
      "org.apache.kafka"  % "kafka-clients"             % kafkaVersion,
      "io.kamon"          %% "kamon-bundle"             % kamonVersion,
      "io.kamon"          %% "kamon-akka"               % kamonVersion,
      "io.kamon"          %% "kamon-prometheus"         % kamonVersion,

      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"                % "3.2.9"         % Test
    )
  )
