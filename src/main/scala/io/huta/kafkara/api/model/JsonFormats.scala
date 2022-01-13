package io.huta.kafkara.api.model

import io.huta.kafkara.user.UserRegistry.ActionPerformed
import io.huta.kafkara.user.{User, Users}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object JsonFormats {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val userJsonFormat = jsonFormat3(User)
  implicit val usersJsonFormat = jsonFormat1(Users)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)

  implicit val topicInfoFormat = jsonFormat2(TopicInfo)
  implicit val topicInfoListFormat: RootJsonFormat[TopicsInfo] = jsonFormat1(TopicsInfo)
}
