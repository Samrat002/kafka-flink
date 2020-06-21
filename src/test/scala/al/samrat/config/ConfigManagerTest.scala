package al.samrat.config
import org.scalatest._
import flatspec._
import matchers._

class ConfigManagerTest extends AnyFlatSpec with should.Matchers {

  val configManagerInstance = new ConfigManager(path = "/Users/recko/big_data/repos/kafka-flink/src/main/resources/base.conf")
  "Application Name " should "Flink-Kakfa" in {
    configManagerInstance.applicationName should be ("Flink-Kakfa")
  }
  "Destination Name" should  "return the destination as kafka" in {
    configManagerInstance.destination should be ("kafka")
  }

  "Broker List " should "get the broker list in string" in {
    configManagerInstance.brokerList should be ("localhost:9092")
  }

//  "Source Meta data " should "provid the source related details" in {
//    configManagerInstance.Source.source.map(d => s"${d._1} -> ${d._2}").mkString(" | ") should be ()
//  }


}
