package com.bitparadigm

case class TranslatedStatement (
  output: String,
  source: ParsedStatement,
  pc: Long
) {
  lazy val count = source.command match {
    case CommandType.Label => 0
    case CommandType.Function => output.lines.length - 1
    case _ => output.lines.length
  }

  def outputWithComment(optionalComment: Option[String] = None) = {
    s"""
      |${optionalComment.map("// " + _).getOrElse("")}
      |// ${source.raw}
      |${output}
    """.stripMargin.trim
  }
}
