package pl.msulima.correlator

import scala.io.Source

object Correlator extends App {

  private val escape = 27.toChar
  private val picker = {
    val s = Source.fromInputStream(getClass.getResourceAsStream("/colors.cfg"))
    try {
      new ColorPicker(s.getLines().map(line => {
        val chunks = line.split(" ")
        val threshold = chunks(0).toDouble
        val code = chunks(1)
        threshold -> code
      }).toVector)
    } finally {
      s.close()
    }
  }
  // print palette
  (-10 to 10).foreach(x => printCell(x.toDouble / 10))
  println()
  println()

  private val seriesNames = Seq("serie1.txt", "serie2.txt", "s3.txt")

  private val series = CrossCorrelator.pearson(seriesNames.map(serie => {
    readSource(() => Source.fromFile(serie))
  }))

  private val longestName = seriesNames.map(_.size).max
  print(" " * longestName + "   ")
  println((0 until series.size).map(idx => {
    val letter = (idx + 'A').toChar
    f"$letter%3s  "
  }).mkString(" "))
  for {
    i <- 0 until series.size
  } {
    val row = series(i)
    val serieName = seriesNames(i)
    val letter = (i + 'A').toChar
    print(" " * (longestName - serieName.length) + serieName + f"$letter%3s ")
    row.foreach(printCell)
    println()
  }

  private def readSource(source: () => Source): Seq[Double] = {
    val s = source()

    try {
      s.getLines().toVector.map(_.toDouble)
    } finally {
      ex: RuntimeException =>
        s.close()
    }
  }

  private def printCell(value: Double) = {
    print(escape + s"[${picker(value)}m")
    print(f"$value% 2.2f ")
    print(escape + "[0m")
  }
}
