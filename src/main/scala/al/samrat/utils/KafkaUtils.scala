package al.samrat.utils

import java.util.Properties

import al.samrat.config.ConfigManager

object KafkaUtils {

  def getProperties(configs:ConfigManager, identifier:String):Properties = {
    val prop = new Properties()
    prop.setProperty("bootstrap.servers",configs.brokers)
    prop.setProperty("zookeeper.connect", "localhost:2181")
    prop.setProperty("group.id",
      if(identifier == "source")
        configs.Source.source.asInstanceOf[Map[String,String]]("topic")
      else
        configs.Destination.destinationTopic
    )
    prop
  }



}
