package ml

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.feature.Word2Vec
import org.apache.spark.rdd.RDD

object Word2Vector{
  def main(args: Array[String]) {
    val file = "/home/ubuntu/work/spark/spark-samples/ml-samples/data/text8_10000"
    val conf = new SparkConf().setAppName("Word2Vector")
    val sc = new SparkContext(conf)
    val input = sc.textFile(file).map(line => line.split(" ").toSeq)
    val word2vec = new Word2Vec()
    val model = word2vec.fit(input)
    val synonyms = model.findSynonyms("anarchist", 40)
    for((synonym, cosineSimilarity) <- synonyms) {
      println(s"$synonym $cosineSimilarity")
    }
  }
}