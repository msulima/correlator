package pl.msulima.correlator

import org.specs2.mutable.Specification

class FileParserTest extends Specification {

  "parses raw files" in {
    // given
    val file =
      """upper_25,1426087200,1426094640,240|3.0,2.5,2.25,None
        |upper_75,1426087200,1426094640,240|1,2,3,None
        |sum(foo, bar),1426087200,1426094640,240|-1,None,3,4""".stripMargin


    // when
    val result = FileParser.raw(file.lines).toVector

    // then
    result must_== Seq(
      "upper_25" -> Seq(3.0, 2.5, 2.25, 0d),
      "upper_75" -> Seq(1d, 2d, 3d, 0d),
      "sum(foo, bar)" -> Seq(-1d, 0d, 3d, 4d)
    )
  }
}
