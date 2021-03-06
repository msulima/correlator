package pl.msulima.correlator

import java.net.MalformedURLException

import scala.io.Source

object Correlator extends App {

  if (args.isEmpty) {
    sys.error("usage: [seriesFile]")
    sys.exit(1)
  }

  private val series = {
    val sourceName = args(0)
    readSource(() => {
      try {
        Source.fromURL(sourceName)
      } catch {
        case ex: MalformedURLException =>
          Source.fromFile(sourceName)
      }
    })
  }

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
  printPalette()

  private val seriesNames = series.map(_._1)
  private val correlations = CrossCorrelator.pearson(series.map(_._2))

  private val longestName = seriesNames.map(_.size).max

  private val NameThreshold = 30
  if (longestName > NameThreshold) {
    for (i <- series.indices) {
      println(printStriped(i, s"${assignLetter(i)} = ${seriesNames(i)}"))
    }
    println()
    print("    ")
  } else {
    print(" " * longestName + "     ")
  }

  println(series.indices.map(i => s"${assignLetter(i)}  ").mkString(" "))

  for {
    i <- series.indices
  } {
    val serieName = seriesNames(i)
    val namePrefix = if (longestName <= NameThreshold) {
      s"${" " * (longestName - serieName.length)}$serieName "
    } else {
      ""
    }
    print(namePrefix + assignLetter(i) + " ")
    val row = correlations(i)
    row.dropRight(1).foreach(printCell)
    for {
      j <- i until correlations.size
    } printCellFaded(correlations(j)(i))

    println()
  }

  private def readSource(source: () => Source): Seq[(String, Seq[Double])] = {
    val s = source()

    try {
      FileParser.raw(s.getLines()).toVector
    } finally {
      ex: RuntimeException =>
        s.close()
    }
  }

  private def assignLetter(idx: Int) = {
    val letter = (idx + 'A').toChar
    f"$letter%3s"
  }

  private def printCell(value: Double) = {
    print(escape + s"[${picker(value)}m")
    print(f"$value% 2.2f ")
    print(escape + "[0m")
  }

  private def printStriped(idx: Int, text: String) = {
    if (idx % 2 == 0) {
      escape + "[48;5;238m" + text + escape + "[0m"
    } else {
      text
    }
  }

  private def printCellFaded(value: Double) = {
    print(escape + "[38;5;242m")
    print(f"$value% 2.2f ")
    print(escape + "[0m")
  }

  private def printPalette(): Unit = {
    (-10 to 10).foreach(x => printCell(x.toDouble / 10))
    println()
    println()
  }
}
