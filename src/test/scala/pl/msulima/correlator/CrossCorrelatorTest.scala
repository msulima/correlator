package pl.msulima.correlator

import org.specs2.mutable.Specification

class CrossCorrelatorTest extends Specification {

  "correlate many series" in {
    // given
    val series = Seq(Seq(1d, 2d, 3d), Seq(1d, 1d, 1d), Seq(3d, 2d, 1d))

    // when
    val result = CrossCorrelator.pearson(series)

    // then
    result.map(_.map(Math.round)) must_== Seq(Seq(1.0), Seq(0.0, 0.0), Seq(-1.0, 0.0, 1.0))
  }
}
