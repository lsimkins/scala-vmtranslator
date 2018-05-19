package com.bitparadigm

import com.bitparadigm.VMTranslator.file

import scala.io.Source
import scala.util.Try

class ProgramParser(val lines: Iterator[String], context: String = "") {
  lazy val rawStatements: Iterator[String] =
    lines.filter(isStatement)

  lazy val statements: Iterator[ParsedStatement] =
    rawStatements
      .map(r => ParsedStatement.parse(r).copy(context = context))

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
    val basename = filename.split("\\.").head
    Try(new ProgramParser(Source.fromFile(filename).getLines(), basename))
  }
}
