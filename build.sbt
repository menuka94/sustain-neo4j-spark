
name := "sustain-neo4j-spark"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "org.mongodb.spark" %% "mongo-spark-connector" % "3.0.1",
  "org.apache.spark" %% "spark-core" % "3.0.1",
  "org.apache.spark" %% "spark-sql" % "3.0.1",
  "org.apache.commons" % "commons-math3" % "3.2",
  "com.github.scopt" %% "scopt" % "3.7.0",
  "org.apache.spark" %% "spark-mllib" % "3.0.1" % "provided",
  "org.neo4j" % "neo4j-connector-apache-spark_2.12" % "4.0.2_for_spark_3"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

test in assembly := {}

