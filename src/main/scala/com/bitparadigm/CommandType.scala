package com.bitparadigm

abstract class CommandType
abstract class BranchingCommand extends CommandType
abstract class MemorySegmentCommand extends CommandType
abstract class FunctionCommand extends CommandType
abstract class ArithmeticCommand extends CommandType

object CommandType {
  case object Push extends MemorySegmentCommand
  case object Pop extends MemorySegmentCommand

  case object Function extends FunctionCommand
  case object Return extends FunctionCommand
  case object Call extends FunctionCommand

  case object Label extends BranchingCommand
  case object GoTo extends BranchingCommand
  case object IfGoTo extends BranchingCommand

  case object Add extends ArithmeticCommand
  case object Sub extends ArithmeticCommand
  case object Neg extends ArithmeticCommand
  case object Eq extends ArithmeticCommand
  case object Gt extends ArithmeticCommand
  case object Lt extends ArithmeticCommand
  case object And extends ArithmeticCommand
  case object Or extends ArithmeticCommand
  case object Not extends ArithmeticCommand

  case object Invalid extends CommandType

  val MemorySegmentCommands = Set("push", "pop")
  val BranchingCommands = Set("label", "goto", "if-goto")
  val FunctionCommands = Set("function", "return", "call")
  val ArithmeticCommands = Set("add", "sub", "neg", "eq", "gt", "lt", "and", "or", "not")

  val commandMap: Map[String, CommandType] = Map(
    "push"     -> Push,
    "pop"      -> Pop,
    "label"    -> Label,
    "goto"     -> GoTo,
    "if-goto"  -> IfGoTo,
    "function" -> Function,
    "return"   -> Return,
    "call"     -> Call,
    "add"      -> Add,
    "sub"      -> Sub,
    "neg"      -> Neg,
    "eq"       -> Eq,
    "gt"       -> Gt,
    "lt"       -> Lt,
    "and"      -> And,
    "or"       -> Or,
    "not"      -> Not
  )

  def mapType(command: String): CommandType = {
    commandMap.getOrElse(command, Invalid)
  }
}