package al.samrat.runner

import al.samrat.config.ConfigManager
import al.samrat.serialization.KafkaStringSchema
import al.samrat.utils.KafkaUtils._
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, FlinkKafkaProducer}
import org.apache.flink.table.api.Table
import org.apache.flink.table.api.scala.StreamTableEnvironment
import org.apache.flink.table.descriptors.Kafka

class DagRunner(configs: ConfigManager,
                senv: StreamExecutionEnvironment) {

  implicit val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(senv)

  def run(): Unit = {
    configs.sourceName match {
      case "kafka" =>
        loadToKafka( transform (extractFromKafka))
        tableEnv.execute("Table Operations!!!!")
      case "file" =>
      case _ =>

    }
  }

  def extractFromKafka = {


    val format = configs.Source.dataFormat
    val topic = configs.Source.source("topic")
    val properties = getProperties(configs, "source")
    val kafkaConsumer = new FlinkKafkaConsumer[String](topic, new SimpleStringSchema(), properties)
    format match {
      case "csv" =>
        val newStream = senv.addSource(kafkaConsumer)
        tableEnv.fromDataStream(newStream)

//      case "json" =>
//        val kafkaConsumer = new FlinkKafkaConsumer[]()

      case _ =>
        val table = tableEnv.fromDataStream(senv.addSource(kafkaConsumer))
        tableEnv.createTemporaryView( "tempTable", table)
        table
    }


  }

//  def extractFromFile:Table = {
//
//  }

  def transform(streamTable: Table):Table = streamTable

  def loadToKafka(streamTable: Table) = {
    val kafkaProducer = new FlinkKafkaProducer[String](
      configs.brokers,
      configs.Destination.destinationTopic,
      KafkaStringSchema

    )
    tableEnv.toAppendStream[String](streamTable).addSink(kafkaProducer)

  }
}
