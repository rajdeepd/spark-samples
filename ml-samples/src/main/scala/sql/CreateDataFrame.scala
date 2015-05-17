package sql 

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object CreateDataFrame {
	def main(args: Array[String]) {
		val dataFile = "/home/ubuntu/work/spark-src/spark/examples/src/main/resources/people.json" // Should be some file on your system
		val conf = new SparkConf().setAppName("Simple Application")
		val sc = new SparkContext(conf)
		val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val df = sqlContext.jsonFile(dataFile)
    df.show()
	}
}

