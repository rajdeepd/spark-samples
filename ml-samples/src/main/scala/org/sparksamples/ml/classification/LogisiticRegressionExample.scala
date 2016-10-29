package org.sparksamples.ml

import org.apache.spark.SparkConf
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.sql.SparkSession

/**
  * Created by Rajdeep Dua on 10/29/16.
  */

object LogisticRegressionExample {
  val PATH = "/home/ubuntu/work/spark-2.0.0-bin-hadoop2.7/";

  def main(args: Array[String]): Unit = {
    val spConfig = (new SparkConf).setMaster("local[1]").setAppName("SparkApp").
      set("spark.driver.allowMultipleContexts", "true")

    val spark = SparkSession
      .builder()
      .appName("Spark SQL Example")
      .config(spConfig)
      .getOrCreate()

    val training = spark.read.format("libsvm").load(PATH + "data/mllib/sample_libsvm_data.txt")
    import org.apache.spark.ml.classification.LogisticRegression
    val lr = new LogisticRegression()
      .setMaxIter(10)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)

    // Fit the model
    val lrModel = lr.fit(training)

    // Print the coefficients and intercept for logistic regression
    println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")

    val logisticRegressinSummary = lrModel.evaluate(training)
    val predictions = logisticRegressinSummary.predictions
    predictions.show()

    spark.stop()
  }
}
