
name := "kafka-flink"

version := "0.1"
scalaVersion := "2.12.11"
val flinkVersion = "1.10.0"

resolvers += "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository"

// https://mvnrepository.com/artifact/org.apache.flink/flink-streaming-scala
// https://mvnrepository.com/artifact/org.apache.flink/flink-table-planner
// https://mvnrepository.com/artifact/org.apache.flink/flink-core
// https://mvnrepository.com/artifact/org.apache.flink/flink-connector-kafka
// https://mvnrepository.com/artifact/com.typesafe/config
// https://mvnrepository.com/artifact/org.scalatest/scalatest

libraryDependencies ++= Seq(

  // flink dependencies
  "org.apache.flink" %% "flink-streaming-scala" % "1.10.0" % "provided",
  "org.apache.flink" %% "flink-table-planner" % "1.10.0" % "provided",
  "org.apache.flink" % "flink-core" % "1.10.0",
  "org.apache.flink" %% "flink-connector-kafka" % "1.10.0",
  // type safe config
  "com.typesafe" % "config" % "1.4.0",
  // scala test
  "org.scalatest" %% "scalatest" % "3.2.0" % Test




)





assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

artifact in(Compile, assembly) := {
  val art = (artifact in(Compile, assembly)).value
  art.withClassifier(Some("assembly"))
}

addArtifact(artifact in(Compile, assembly), assembly)


