package com.bitparadigm


object MemorySegment {
  type MemorySegment = String

  val argument: MemorySegment = "ARG"
  val local: MemorySegment = "LCL"
  val _this: MemorySegment = "THIS"
  val that: MemorySegment = "THAT"
  val static: MemorySegment = "static"
  val constant: MemorySegment = "constant"
  val temp: MemorySegment = "temp"
  val pointer: MemorySegment = "pointer"

  def mapMemorySegment(segment: String): MemorySegment = {
    segment match {
      case "argument" => argument
      case "local" => local
      case "this" => _this
      case "that" => that
      case "static" => static
      case "constant" => constant
      case "temp" => temp
      case "pointer" => pointer
      case _ => throw CompilerError("Invalid memory segment", new Throwable(segment))
    }
  }
}
