package al.samrat.runner

import al.samrat.config.ConfigManager
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import al.samrat.utils.KafkaUtils._
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.table.api.TableEnvironment
import org.apache.flink.table.api.scala.StreamTableEnvironment
import org.apache.flink.streaming.api.scala._

class DagRunner(configs:ConfigManager,
                senv:StreamExecutionEnvironment) {

  def run(): Unit ={

    //extract
    configs.sourceName match {
      case "kafka" => extractFromKafka
    }
    val stream  = extract
    val transformStream = transform stream
    load transformStream
    //transform
    //load

  }

  def extractFromKafka = {
    val tableEnv = StreamTableEnvironment.create(senv)

      val format = configs.Source.dataFormat
      val topic  = configs.Source.source("topic")
      val properties = getProperties(configs, "source")
      format match {
        case "csv" =>

        case "json" =>
          val kafkaConsumer = new FlinkKafkaConsumer[Json]()

        case _ =>
          val kafkaConsumer = new FlinkKafkaConsumer[String](topic, new SimpleStringSchema(), properties )
          tableEnv.fromDataStream(senv.addSource(kafkaConsumer))
      }




  }

  def transform(stream:Stream): Stream = ???

  def load()
}
