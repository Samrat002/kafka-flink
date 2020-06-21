package al.samrat

import al.samrat.runner.DagRunner
import al.samrat.config.ConfigManager
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.slf4j.{Logger, LoggerFactory}

object Processor {

  private val logger:Logger = LoggerFactory.getLogger(this.getClass)

  logger.info("Initializing the flink environment")
  val senv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
  senv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

  def main(args: Array[String]): Unit = {
    val path = args(0)
    logger.info(s"Configuration File path received:- ${path}")
    implicit val configs = new ConfigManager(path)
    val runner  = new DagRunner(configs, senv)
    runner.run
    senv.execute("Job Execution started")
  }
}
