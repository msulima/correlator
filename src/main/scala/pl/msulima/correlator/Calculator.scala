package pl.msulima.correlator

object Calculator {

  private val Epsilon = 1e-5

  def pearson(xs: Seq[Double], ys: Seq[Double]): Double = {
    if (xs.isEmpty) {
      new IllegalArgumentException("xs is empty")
    } else if (xs.isEmpty) {
      new IllegalArgumentException("ys is empty")
    } else if (xs.size != ys.size) {
      new IllegalArgumentException("xs and ys have different sizes")
    }

    val avgX = avg(xs)
    val avgY = avg(ys)

    val denominator = standardDeviation(xs, avgX) * standardDeviation(ys, avgY)

    val numerator = xs.zip(ys).map({
      case (x, y) =>
        (x - avgX) * (y - avgY)
    }).sum

    if (denominator > -Epsilon && denominator < Epsilon) {
      0
    } else {
      numerator / denominator
    }
  }

  private def avg(xs: Seq[Double]) = {
    xs.sum.toDouble / xs.size
  }

  private def standardDeviation(xs: Seq[Double], avgX: Double) = {
    Math.sqrt(xs.map(x => (x - avgX) * (x - avgX)).sum)
  }
}
