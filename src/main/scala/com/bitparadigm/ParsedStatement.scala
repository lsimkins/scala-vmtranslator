package com.bitparadigm

import MemorySegment._

case class ParsedStatement(
  command: CommandType,
  arg1: Option[MemorySegment] = None,
  arg2: Option[Long] = None,
  raw: String,
  context: String = ""
)
