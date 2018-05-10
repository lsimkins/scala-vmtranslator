package com.bitparadigm

import scala.io.Source
import scala.util.Try

class ProgramParser(val lines: Iterator[String]) {
  lazy val rawStatements: Iterator[String] =
    lines.filter(isStatement)

  lazy val statements: Iterator[ParsedStatement] =
    rawStatements.map(ParsedStatement.parseStatement)

  private val nonCommandMatcher = raw"(?:/{2}).+|(?:)(?!.+)|(?:)[\r\n]+(?!.+)".r
  private def isStatement(line: String): Boolean = {
    !nonCommandMatcher
      .pattern
      .matcher(line)
      .matches
  }
}

object ProgramParser {
  def fromFile(filename: String): Try[ProgramParser] = {
    Try(new ProgramParser(Source.fromFile(filename).getLines()))
  }
}
