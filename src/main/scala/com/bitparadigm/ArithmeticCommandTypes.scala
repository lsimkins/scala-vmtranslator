package com.bitparadigm

abstract class ArithmeticCommandType extends CommandType

object ArithmeticCommandTypes {
  case object Add extends ArithmeticCommandType
  case object Sub extends ArithmeticCommandType
  case object Neg extends ArithmeticCommandType
  case object Eq extends ArithmeticCommandType
  case object Gt extends ArithmeticCommandType
  case object Lt extends ArithmeticCommandType
  case object And extends ArithmeticCommandType
  case object Or extends ArithmeticCommandType
  case object Not extends ArithmeticCommandType

  def mapCommandString(command: String): ArithmeticCommandType = {
    command match {
      case "add" => Add
      case "sub" => Sub
      case "neg" => Neg
      case "eq" => Eq
      case "gt" => Gt
      case "lt" => Lt
      case "and" => And
      case "or" => Or
      case "not" => Not
      case _ => throw CompilerError("Invalid arithmetic command", new Throwable(command))
    }
  }
}
