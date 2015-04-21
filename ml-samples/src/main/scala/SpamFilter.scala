

/**
 * @author ubuntu
 */
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD

object SpamFilter {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SpamFilter")
    val sc = new SparkContext(conf)
    val spam = sc.textFile("/home/ubuntu/work/spark/spark-samples/first-sample/data/spam_v1_text.txt")
    val normal = sc.textFile("/home/ubuntu/work/spark/spark-samples/first-sample/data/non-spam_v1_text.txt")
    
    // Create a HashingTF instance to map email text to vectors of 10,000 features.
    val tf = new HashingTF(numFeatures = 10000)
    // Each email is split into words, and each word is mapped to one feature.
    val spamFeatures = spam.map(email => tf.transform(email.split(" ")))
    val normalFeatures = normal.map(email => tf.transform(email.split(" ")))
    
    // Create LabeledPoint datasets for positive (spam) and negative (normal) examples.
    val positiveExamples = spamFeatures.map(features => LabeledPoint(1, features))
    val negativeExamples = normalFeatures.map(features => LabeledPoint(0, features))
    val trainingData = positiveExamples.union(negativeExamples)
    trainingData.cache() // Cache since Logistic Regression is an iterative algorithm.
    
    // Run Logistic Regression using the SGD algorithm.
    val model = new LogisticRegressionWithSGD().run(trainingData)
    
    // Test on a positive example (spam) and a negative one (normal).
    val sOne = "iPhone Leather Sleeve CASSIOPEIA http://t.co/EMtxZNU2ro | " +
          "#iphone #iphone5 #iPhone5Sleeve #iPhoneSleeve #iPhone5sSleeve #iPhone5s #Swarovski"
    val sTwo = "this is non spam text "
    val sThree = "@airtelghana  thank you soo much #iphone5s​"
    val posTest = tf.transform(sOne.split(" "))
    val negTest = tf.transform(sTwo.split(" "))
    val thirdTest = tf.transform(sThree.split(" "))
    println("Prediction for Spam '" + sOne  + "' : "+ model.predict(posTest))
    println("Prediction for spam '" + sTwo + "' : " + model.predict(negTest))
    println("Prediction for spam '" + sThree + "' :  " + model.predict(thirdTest))
  }
}