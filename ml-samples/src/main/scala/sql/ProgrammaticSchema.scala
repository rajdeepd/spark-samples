package sql 

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SchemaRDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StructType,StructField,StringType}

object ProgrammaticSchema {
	def main(args: Array[String]) {
		val dataFile = "/home/ubuntu/work/spark-src/spark/" +
      "examples/src/main/resources/people.txt" 
		val conf = new SparkConf().setAppName("ProgrammaticSchema")
    val sc = new SparkContext(conf)
    val people = sc.textFile(dataFile)
		
	  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val schemaString = "name age"
    val schema = StructType(schemaString.split(" ").
        map(fieldName => StructField(fieldName, StringType, true)))

    // Convert records of the RDD (people) to Rows.
    val rowRDD = people.map(_.split(",")).map(p => Row(p(0), p(1).trim))
    val peopleDataFrame = sqlContext.createDataFrame(rowRDD, schema)

    // Register the DataFrames as a table.
    peopleDataFrame.registerTempTable("people")
    
    // SQL statements can be run by using the sql methods provided by sqlContext.
    val results = sqlContext.sql("SELECT name FROM people")
    
    // The results of SQL queries are DataFrames and support all the normal RDD operations.
    // The columns of a row in the result can be accessed by ordinal.
    results.map(t => "Name: " + t(0)).collect().foreach(println)

  }
}

