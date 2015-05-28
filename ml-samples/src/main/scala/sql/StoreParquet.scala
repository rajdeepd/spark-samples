package sql 

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SchemaRDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StructType,StructField,StringType}

object StoreParquet {
	def main(args: Array[String]) {
		val dataFile = 
      "/home/ubuntu/work/spark-src/spark/examples/" + 
      "src/main/resources/people.txt" 
		val conf = new SparkConf().setAppName("StoreParquet")
    val sc = new SparkContext(conf)
    val people = sc.textFile(dataFile)
    val schemaString = "name age"
		val schema = StructType(schemaString.split(" ").map(
        fieldName => StructField(fieldName, StringType, true)))

	  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val rowRDD = people.map(_.split(",")).map(p => Row(p(0), p(1).trim))
    val peopleDataFrame = sqlContext.createDataFrame(rowRDD, schema)
    peopleDataFrame.saveAsParquetFile("output/people.parquet")

  }
}

