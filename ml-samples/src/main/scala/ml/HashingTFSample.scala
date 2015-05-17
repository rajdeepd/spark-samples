package ml

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD

object HashingTFSample{
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SpamFilter")
    val sc = new SparkContext(conf)
    val sentence = "This is a simple text"
    val words = sentence.split(" ")
    val tf = new HashingTF(numFeatures = 10000)
    val sparseVector = words.map(w => tf.transform(w))
    for(sv <- sparseVector) {
      println(s"$sv")
    }
  }
}