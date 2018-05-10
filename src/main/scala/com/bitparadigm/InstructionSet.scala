package com.bitparadigm

case class InstructionSet (
  output: String,
  source: ParsedStatement,
  pc: Long
) {
  lazy val count = output.split("\n").length

  def outputWithComment(optionalComment: Option[String] = None) = {
    s"""
      |${optionalComment.map("// " + _).getOrElse("")}
      |// ${source.raw}
      |${output}
    """.stripMargin.trim
  }
}
