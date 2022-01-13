package io.huta.kafkara.api.model

import scala.collection.immutable

case class TopicInfo(topic: String, partitions: Int)
case class TopicsInfo(topics: immutable.Seq[TopicInfo])
