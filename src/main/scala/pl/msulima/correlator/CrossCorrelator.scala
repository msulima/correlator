package pl.msulima.correlator

object CrossCorrelator {

  def pearson(series: Seq[Seq[Double]]) = {
    (0 until series.size).map(i => {
      (0 to i).map(j => {
        Calculator.pearson(series(i), series(j))
      })
    })
  }
}
