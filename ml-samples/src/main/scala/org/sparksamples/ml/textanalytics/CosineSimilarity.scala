package org.sparksamples.ml.textanalytics

//import org.apache.spark.SparkContext
//import org.apache.spark.SparkConf
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.distributed.{MatrixEntry, RowMatrix}
import org.apache.spark.{SparkConf, SparkContext}

object CosineSimilarity{
  def main(args: Array[String]) {
    val file = "/home/ubuntu/work/spark/spark-samples/ml-samples/data/sample_svm_data.txt"
    val conf = new SparkConf().setAppName("CosineSimilarity").setMaster("local[1]")
    val threshold = 0.1
    val sc = new SparkContext(conf)

    // Load and parse the data file.
    val rows = sc.textFile(file).map { line =>
      val values = line.split(' ').map(_.toDouble)
      Vectors.dense(values)
    }.cache()
    val mat = new RowMatrix(rows)

    // Compute similar columns perfectly, with brute force.
    val exact = mat.columnSimilarities()

    // Compute similar columns with estimation using DIMSUM
    val approx = mat.columnSimilarities(threshold)

    val exactEntries = exact.entries.map { case MatrixEntry(i, j, u) => ((i, j), u) }
    val approxEntries = approx.entries.map { case MatrixEntry(i, j, v) => ((i, j), v) }
    val MAE = exactEntries.leftOuterJoin(approxEntries).values.map {
      case (u, Some(v)) =>
        math.abs(u - v)
      case (u, None) =>
        math.abs(u)
    }.mean()

    println(s"Average absolute error in estimate is: $MAE")

    sc.stop()
  }
}