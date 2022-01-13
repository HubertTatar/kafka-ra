package io.huta.kafkara.config

import com.typesafe.config.Config
import org.apache.kafka.clients.admin.AdminClient

import java.util.Properties

object KafkaClientBootstrap {

  def kafkaClient(config: Config): AdminClient = {
    val bootstrap = config.getConfig("kafka")
    val props = new Properties()
    bootstrap.entrySet().forEach { entry =>
      props.put(entry.getKey, entry.getValue.unwrapped().toString)
    }
    AdminClient.create(props)
  }
}
