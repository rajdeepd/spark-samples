package org.sparksamples.ml.textanalytics

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.HashingTF

object HashingTFSample{
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SpamFilter").setMaster("local[1]")
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