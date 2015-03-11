package pl.msulima.correlator

object FileParser {

  def raw(lines: Iterator[String]): Iterator[(String, Seq[Double])] = {
    lines.map(parseLine)
  }

  private def parseLine(line: String) = {
    val splitted = line.split("\\|")
    val header = splitted(0)
    val data = splitted(1)

    val name = header.split(",").dropRight(3).mkString(",")
    val datapoints = data.split(",").map({
      case "None" => 0d
      case x => x.toDouble
    }).toSeq

    name -> datapoints
  }
}
