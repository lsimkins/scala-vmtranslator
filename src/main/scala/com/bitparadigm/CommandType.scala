package com.bitparadigm

abstract class CommandType

object CommandType {
  case object Push extends CommandType
  case object Pop extends CommandType

  case object Function extends CommandType
  case object Return extends CommandType
  case object Call extends CommandType

  def mapCommandString(command: String): CommandType = {
    command match {
      case "push" => Push
      case "pop" => Pop
      case _ => throw CompilerError("Invalid statement command", new Throwable(command))
    }
  }
}

