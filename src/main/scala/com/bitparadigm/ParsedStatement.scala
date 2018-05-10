package com.bitparadigm

import MemorySegment._

import scala.util.Try

case class ParsedStatement(
  command: CommandType,
  arg1: Option[MemorySegment] = None,
  arg2: Option[Long] = None,
  raw: String
)

object ParsedStatement {
  def parseStatement(str: String): ParsedStatement = {
    val parts = str.split(" ")

    parts.length match {
      case 1 => parseArithmeticStatement(parts.head)
      case 3 => parseStackStatement(parts(0), parts(1), parts(2), str)
      case _ => throw CompilerError("Incorrect number of statement arguments", new Throwable(str))
    }
  }

  def parseArithmeticStatement(commandStr: String): ParsedStatement = {
    ParsedStatement(
      ArithmeticCommandTypes.mapCommandString(commandStr),
      raw = commandStr
    )
  }

  def parseStackStatement(commandStr: String, arg1: String, arg2: String, raw: String): ParsedStatement = {
    ParsedStatement(
      CommandType.mapCommandString(commandStr),
      Some(MemorySegment.mapMemorySegment(arg1)),
      Some(arg2.toLong),
      raw
    )
  }
}
