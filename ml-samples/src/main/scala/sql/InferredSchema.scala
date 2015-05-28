package sql 

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SchemaRDD

object InferredSchema {
	def main(args: Array[String]) {
		val dataFile = "/home/ubuntu/work/spark-src/spark/examples/src/main/resources/people.txt" // Should be some file on your system
		val conf = new SparkConf().setAppName("Simple Application")
		val sc = new SparkContext(conf)
	  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._

    // Define the schema using a case class.
    // Note: Case classes in Scala 2.10 can support only up to 22 fields.
    //To work around this limit,
    // you can use custom classes that implement the Product interface.
    //case class Person(name: String, age: Int)
    
    // Create an RDD of Person objects and register it as a table.
 
    //val people = sc.textFile("/home/ubuntu/work/spark-src/spark/examples/src/main/resources/people.txt").map(_.split(",")).map(p => Person(p(0), p(1).trim.toInt)).toDF()
    //people.registerTempTable("people")
    
    // SQL statements can be run by using the sql methods provided by sqlContext.
    //val teenagers = sqlContext.sql("SELECT name FROM people WHERE age >= 13 AND age <= 19")
    
    // The results of SQL queries are DataFrames and support all the normal RDD operations.
    // The columns of a row in the result can be accessed by ordinal.
    //teenagers.map(t => "Name: " + t(0)).collect().foreach(println)
 
  }
}

