package pl.msulima.correlator

import scala.annotation.tailrec

class ColorPicker(colors: Seq[(Double, String)]) extends Function[Double, String] {

  override def apply(value: Double) = {
    val roundedValue = Math.round(value * 100).toDouble / 100

    @tailrec
    def color0(colors: Seq[(Double, String)]): String = {
      if (colors.size == 1) {
        colors.head._2
      } else {
        val (left, right) = colors.splitAt(colors.size / 2)

        if (right.head._1 > roundedValue) {
          color0(left)
        } else {
          color0(right)
        }
      }
    }

    color0(colors)
  }
}
