package pl.msulima.correlator

import org.specs2.matcher.DataTables
import org.specs2.mutable.Specification

class ColorPickerTest extends Specification with DataTables {

  private val colors = Seq(-1.0 -> "1", 0.0 -> "2", 0.5 -> "3")
  private val picker = new ColorPicker(colors)

  "picks color" in
    "value" || "colos ascii code" |
      -1.0 !! "1" |
      -0.5 !! "1" |
      0.0 !! "2" |
      0.5 !! "3" |
      1.0 !! "3" |> {
      (value: Double, color: String) =>
        // when
        val result = picker(value)

        // then
        result must_== color
    }
}
