package com.bitparadigm

import scala.io.Source
import scala.util.Try

class ProgramParser(val lines: Iterator[String], context: String = "") {
  lazy val rawStatements: Iterator[String] =
    lines.filter(isStatement)

  lazy val statements: Iterator[ParsedStatement] =
    rawStatements
      .map(r => ProgramParser.parse(r).copy(context = context))

  private val nonCommandMatcher = raw"(?:/{2}).+|(?:)(?!.+)|(?:)[\r\n]+(?!.+)".r
  private def isStatement(line: String): Boolean = {
    !nonCommandMatcher
      .pattern
      .matcher(line)
      .matches
  }
}

object ProgramParser {
  def parse(str: String): ParsedStatement = {
    val parts = str.split(" ")
    val command = CommandType.mapType(parts(0))

    command match {
      case (cmd: ArithmeticCommand) => ParsedStatement(cmd, raw=str)
      case (cmd: BranchingCommand)  => ParsedStatement(cmd, Some(parts(1)), raw=str)
      case (cmd: FunctionCommand)   => parseFunctionCommand(cmd, parts, str)
      case (cmd: MemorySegmentCommand) =>
        ParsedStatement(
          cmd,
          Some(MemorySegment.mapMemorySegment(parts(1))),
          Some(parts(2).trim.toLong), // TODO: Add validation and error handling
          raw=str)
      case _ => throw TranslationError("Unrecognized command", new Throwable(str))
    }
  }

  def parseFunctionCommand(cmd: FunctionCommand, parts: Array[String], raw: String) = {
    cmd match {
      case CommandType.Return => ParsedStatement(cmd, raw=raw)
      case CommandType.Function =>
        ParsedStatement(
          cmd,
          Some(parts(1)),
          Some(parts(2).trim.toLong),
          raw)
    }
  }

  def fromFile(filename: String): Try[ProgramParser] = {
    val basename = filename.split("\\.").head
    Try(new ProgramParser(Source.fromFile(filename).getLines(), basename))
  }
}
