package sql 

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object DataFrameOperations {
	def main(args: Array[String]) {
    val dataFile = "/home/ubuntu/work/spark-src/spark/examples/src/main/resources/people.json" // Should be some file on your system
		val conf = new SparkConf().setAppName("Simple Application")
		val sc = new SparkContext(conf)
		val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val df = sqlContext.jsonFile(dataFile)
    df.show()
    // Print the schema in a tree format
    df.printSchema()
    // root
    // |-- age: long (nullable = true)
    // |-- name: string (nullable = true)
    
    // Select only the "name" column
    df.select("name").show()
    // name
    // Michael
    // Andy
    // Justin
    
    // Select everybody, but increment the age by 1
    df.select(df("name"), df("age") + 1).show()
    // name    (age + 1)
    // Michael null
    // Andy    31
    // Justin  20
    
    // Select people older than 21
    df.filter(df("age") > 21).show()
    // age name
    // 30  Andy
    
    // Count people by age
    df.groupBy("age").count().show()
    // age  count
    // null 1
    // 19   1
    // 30   1
	}
}

