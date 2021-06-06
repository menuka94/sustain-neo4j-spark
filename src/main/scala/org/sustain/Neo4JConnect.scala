package org.sustain

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import org.apache.spark.sql.SparkSession

object Neo4JConnect {

  def logEnv(): Unit = {
    println(">>> Environment")
    println(s"SPARK_MASTER: $Constants.SPARK_MASTER")
    println(s"DB_HOST: $Constants.DB_HOST")
    println(s"DB_PORT: $Constants.DB_PORT")
    println(s"NEO4J_BOLT_URL: $Constants.NEO4J_BOLT_URL")
  }

  def main(args: Array[String]): Unit = {
    logEnv()

    val collectionName = "osm_points_geo"

    val spark = SparkSession.builder()
      .master(Constants.SPARK_MASTER)
      .appName(s"Neo4j Spark")
      .config("spark.mongodb.input.uri",
        s"mongodb://${Constants.DB_HOST}:${Constants.DB_PORT}/sustaindb.$collectionName")
      .getOrCreate()

    val sc = spark.sparkContext

     val df = spark.read.format("org.neo4j.spark.DataSource")
      .option("url", Constants.NEO4J_BOLT_URL)
      .option("authentication.basic.username", Constants.NEO4J_USERNAME)
      .option("authentication.basic.password", Constants.NEO4J_PASSWORD)
      .option("labels", "Person")
      .load()
      df.show()

    // val jsondata = spark.read.json("/s/lattice-100/c/nobackup/galileo/example_2.json")
    // print(jsondata)

    // Read OSM data from MongoDB

    val osmPointsDF = MongoSpark.load(spark,
      ReadConfig(Map("collection" -> collectionName, "readPreference.name" -> "secondaryPreferred"), Some(ReadConfig(sc))))

    osmPointsDF.show()

    //val osmDF = spark.sqlContext.read.format("osm.pbf").load("/s/parsons/b/others/sustain/Downloads/north-america-latest.osm.pbf")
    //osmDF: org.apache.spark.sql.DataFrame = [id: bigint, lat: tinyint, lon: tinyint,keys: bigint, vals: bigint,info: bigint]
/*val osmDF = spark.read.format(OsmSource.OSM_SOURCE_NAME).load("/s/parsons/b/others/sustain/Downloads/north-america-latest.osm.pbf")
    foreachprintln(osmDF)
    osmDF.createOrReplaceTempView("osm")

     spark.sql("select type, count(*) as num_primitives from osm group by type").show() */
    println("Completed!")
  }
}
