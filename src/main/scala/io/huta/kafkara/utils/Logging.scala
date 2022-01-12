package io.huta.kafkara.utils

import org.log4s.{Logger, getLogger}

trait Logging {
  val log: Logger = getLogger(getClass)
}
