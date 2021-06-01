package org.sustain

import org.apache.spark.sql.SparkSession

object Neo4JConnect {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master(Constants.SPARK_MASTER)
      .appName(s"Neo4j Spark")
      .getOrCreate()

    val sc = spark.sparkContext

    val df = spark.read.format("org.neo4j.spark.DataSource")
      .option("url", Constants.NEO4J_BOLT_URL)
      .option("authentication.basic.username", Constants.NEO4J_USERNAME)
      .option("authentication.basic.password", Constants.NEO4J_PASSWORD)
      .option("labels", "Person")
      .load()

    df.show()
  }
}
