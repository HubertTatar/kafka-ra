package io.huta.kafkara.topic

import akka.util.Timeout
import io.huta.kafkara.api.model.{TopicInfo, TopicsInfo}
import org.apache.kafka.clients.admin.AdminClient

import scala.concurrent.{ExecutionContext, Future}
import scala.jdk.CollectionConverters._

class TopicInfoProvider(client: AdminClient)(implicit executor: ExecutionContext, timeout: Timeout) {

  private val timeoutMillis = timeout.duration.toMillis
  private val timeUnit = timeout.duration.unit

  def listTopics(): Future[TopicsInfo] = {
    Future {
      val topicNames = client
        .listTopics()
        .names()
        .get(timeoutMillis, timeUnit)

      val topicDescriptions = client
        .describeTopics(topicNames)
        .all()
        .get(timeoutMillis, timeUnit)
        .asScala
        .values

      val res = topicDescriptions.map { description =>
        TopicInfo(
          description.name(),
          description.partitions().size()
        )
      }.toSeq
      TopicsInfo(res)
    }
  }

}
