package org.sparksamples.basic

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import java.util.Calendar

object WordCount {
  def main(args: Array[String]) {
    val logFile = "/home/ubuntu/work/spark/spark-samples/ml-samples/data/README.md"
    val conf = new SparkConf().setAppName("Word Count").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val start =  System.currentTimeMillis
    val input = sc.textFile(logFile, 2).cache()
 
    val words = input.flatMap(x => x.split(" "))
    val pairs = words.map(s => (s,1))
    val counts = pairs.reduceByKey((a,b) => (a + b))
    val end =  System.currentTimeMillis
    val d = end - start
    counts.foreach(println)
    println("TimeTaken in ms" + d)
  }
}
