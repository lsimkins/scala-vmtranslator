package com.bitparadigm

import scala.io.Source
import scala.util.Try

class ProgramParser(val lines: Iterator[String], context: String = "") {
  lazy val rawStatements: Iterator[String] =
    lines.filter(ProgramParser.isStatement)

  lazy val statements: Iterator[ParsedStatement] =
    rawStatements
      .map(r => ProgramParser.parse(r, context).copy(context = context))
}

object ProgramParser {
  def parse(str: String, context: String = ""): ParsedStatement = {
    val parts = str.split("( |\t)")
    val command = CommandType.mapType(parts(0))

    command match {
      case (cmd: ArithmeticCommand) =>
        ParsedStatement(cmd, raw=str, context=context)
      case (cmd: BranchingCommand) =>
        ParsedStatement(cmd, Some(parts(1)), raw=str, context=context)
      case (cmd: FunctionCommand) =>
        parseFunctionCommand(cmd, parts, str, context)
      case (cmd: MemorySegmentCommand) =>
        ParsedStatement(
          cmd,
          Some(MemorySegment.mapMemorySegment(parts(1))),
          Some(parts(2).trim.toLong), // TODO: Add validation and error handling
          str,
          context)
      case _ => throw TranslationError("Unrecognized command", new Throwable(str))
    }
  }

  def parseFunctionCommand(cmd: FunctionCommand, parts: Array[String], raw: String, context: String) = {
    cmd match {
      case CommandType.Return => ParsedStatement(cmd, raw=raw, context=context)
      case CommandType.Function | CommandType.Call =>
        ParsedStatement(
          cmd,
          Some(parts(1)),
          Some(parts(2).trim.toLong),
          raw,
          context)
    }
  }

  def fromFile(filename: String): Try[ProgramParser] = {
    val basename = filename.split("\\.").head
    Try(new ProgramParser(Source.fromFile(filename).getLines(), basename))
  }

  private val nonCommandMatcher = raw"(?:/{2}).*|(?:)(?!.+)|(?:)[\r\n]+(?!.+)".r
  def isStatement(line: String): Boolean = {
    !nonCommandMatcher
      .pattern
      .matcher(line)
      .matches
  }
}
