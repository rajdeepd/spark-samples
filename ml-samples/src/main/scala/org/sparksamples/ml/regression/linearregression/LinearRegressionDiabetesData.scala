package org.sparksamples.ml.regression.linearregression

import org.apache.spark.SparkConf
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.SparkSession
import org.sparksamples.ml.regression.Util

import scala.collection.Map
import scala.collection.mutable.ListBuffer

/**
  * LogisticalRegression App
  * @author Rajdeep Dua
  */
object LinearRegressionDiabetesData{
  def main(args: Array[String]) {
    val spConfig = (new SparkConf).setMaster("local[1]").setAppName("SparkApp").
      set("spark.driver.allowMultipleContexts", "true")

    val spark = SparkSession
      .builder()
      .appName("Spark Linear Regression")
      .config(spConfig)
      .getOrCreate()
    val training = spark.read.format("libsvm")
      .load("./data/diabetes_libsvm/part-00000")

    val lr = new LinearRegression()
      .setMaxIter(10)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)

    // Fit the model
    val lrModel = lr.fit(training)
    val prediction = lrModel.transform(training)
    prediction.show(20)

    // Print the coefficients and intercept for linear regression
    println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")

    // Summarize the model over the training set and print out some metrics
    val trainingSummary = lrModel.summary
    println(s"numIterations: ${trainingSummary.totalIterations}")
    println(s"objectiveHistory: ${trainingSummary.objectiveHistory.toList}")
    trainingSummary.residuals.show()
    println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
    println(s"r2: ${trainingSummary.r2}")


  }

}
