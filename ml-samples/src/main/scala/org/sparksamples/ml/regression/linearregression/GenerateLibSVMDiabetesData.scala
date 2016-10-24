package org.sparksamples.ml.regression.linearregression

import org.apache.spark.ml.regression.LinearRegression
import org.sparksamples.ml.regression.Util

/**
  * @author Rajdeep Dua
  */
object GenerateLibSVMDiabetesData{
  def main(args: Array[String]) {
    val diabetesRdd = Util.saveDiabetesDataAsLibSVM()
  }
}
