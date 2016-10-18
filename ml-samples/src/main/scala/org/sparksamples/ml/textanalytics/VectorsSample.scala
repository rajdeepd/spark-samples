package org.sparksamples.ml.textanalytics

import org.apache.spark.mllib.linalg.Vectors

object VectorsSample {
  def main(args: Array[String]) {
    // Create the dense vector <1.0, 2.0, 3.0>; Vectors.dense takes values or an array
    val denseVec1 = Vectors.dense(1.0, 2.0, 3.0)
    val denseVec2 = Vectors.dense(Array(1.0, 2.0, 3.0))
    println("Dense Vector 1 : " + denseVec1)
    println("Dense Vector 2 : " + denseVec2)
    // Create the sparse vector <1.0, 0.0, 2.0, 0.0>; Vectors.sparse takes the size of
    // the vector (here 4) and the positions and values of nonzero entries
    val sparseVec1 = Vectors.sparse(4, Array(0, 2), Array(1.0, 2.0))
    println("Sparse Vector:" + sparseVec1)
  }
}