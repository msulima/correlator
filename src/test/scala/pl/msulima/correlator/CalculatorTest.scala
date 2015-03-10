package pl.msulima.correlator

import org.specs2.matcher.DataTables
import org.specs2.mutable.Specification

class CalculatorTest extends Specification with DataTables {

  "calculate Pearson correlation factor" in
    "first" || "second" | "correlation" |
      Seq(1, 2, 3) !! Seq(1, 2, 3) ! 1.0 |
      Seq(1, 2, 3) !! Seq(3, 2, 1) ! -1.0 |
      Seq(1, 2, 3) !! Seq(1, 1, 1) ! 0.0 |
      Seq(1, 2, 3) !! Seq(0, 0, 0) ! 0.0 |> {
      (first: Seq[Int], second: Seq[Int], correlation: Double) =>
        // when
        val result = Calculator.pearson(first.map(_.toDouble), second.map(_.toDouble))

        // then
        result must beCloseTo(correlation within 2.significantFigures)
    }
}
