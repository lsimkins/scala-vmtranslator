package com.bitparadigm

import MemorySegment._

case class ParsedStatement(
  command: CommandType,
  arg1: Option[MemorySegment] = None,
  arg2: Option[Long] = None,
  raw: String,
  context: String = ""
)
object ParsedStatement {
  def parse(str: String): ParsedStatement = {
    val parts = str.split(" ")
    val command = CommandType.mapType(parts(0))

    command match {
      case (cmd: ArithmeticCommand) =>
        ParsedStatement(cmd, raw=str)
      case (cmd: BranchingCommand) =>
        ParsedStatement(cmd, Some(parts(1)), raw=str)
      case (cmd: MemorySegmentCommand) =>
        ParsedStatement(
          cmd,
          Some(MemorySegment.mapMemorySegment(parts(1))),
          Some(parts(2).toLong), // TODO: Add validation and error handling
          raw=str)
      case _ => throw TranslationError("Unrecognized command", new Throwable(str))
    }
  }
}
