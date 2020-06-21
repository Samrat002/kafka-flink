package al.samrat.config

import java.io.File
import com.typesafe.config.{Config, ConfigFactory}

class ConfigManager(path:String) {

  private lazy val conf: Config = ConfigFactory.parseFile(new File(path)).getConfig("base")

  val applicationName:String = conf.getString("application_name")
  val sourceName:String = conf.getString("source")
  val destination:String = conf.getString("destination")
  val brokers:String = conf.getString("brokers")

  object Source{
    private val sourceConf = conf.getConfig("source_meta")
    val dataFormat = sourceConf.getString("format")
    val  source: Map[String, String] = sourceName.toLowerCase match {
      case "file" =>
        Map(
          "path" -> sourceConf.getString("path")
        )
        // assuming default as reading from kafka
      case _ =>
        Map(
          "topic" -> sourceConf.getString("source_topic"),
          "brokerList" -> brokers                                             //conf.getString("brokers")
        )

    }


  }

  // assuming kafka is the only destination
  object Destination{
    private val destinationConf = conf.getConfig("destination_meta")
    val destinationTopic = destinationConf.getString("topic")
    val brokerList:String = brokers                                      //  conf.getString("brokers").split(",")

  }

}
