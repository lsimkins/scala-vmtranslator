package com.bitparadigm

import CommandType._
import Commands._
import MemorySegment._

class InstructionCompiler(statements: Seq[ParsedStatement]) {
  val headers = Seq(
    Commands.init
  ).mkString("\n\n")

  val footers = Seq(
    Commands.end.goto,
    Commands.SubRoutine.eq,
    Commands.SubRoutine.lt,
    Commands.SubRoutine.gt,
    Commands.end.label
  ).mkString("\n\n")

  val headersWithComments =
    s"""
      |// Headers
      |${headers}
    """.stripMargin.trim
  val pcStart = -1 + headers.split("\n").size

  val footersWithComments =
    s"""
       |// Footers
       |${footers}
     """.stripMargin.trim

  def compile(): String = {
    val body = compile(statements, pcStart)
      .map(_.output)
      .mkString("\n")

    Seq(headers, body, footers).mkString("\n")
  }

  def compileWithComments(): String = {
    val body = compile(statements, pcStart)
      .map(s => s.outputWithComment(Some(s"PC: ${s.pc.toString}")))
      .mkString("\n\n")

    Seq(headersWithComments, body, footersWithComments).mkString("\n\n")
  }

  def compile(statements: Seq[ParsedStatement], pc: Long = -1): Seq[InstructionSet] = {
    if (statements.isEmpty) {
      Seq.empty
    } else {
      val instructions = compile(statements.head, pc)
      instructions +: compile(statements.tail, pc + instructions.count)
    }
  }

  def compile(statement: ParsedStatement, pc: Long): InstructionSet = {
    val output = statement.command match {
      case Push => compilePush(statement)
      case Pop => compilePop(statement)
      case _ => compileArithmetic(statement, pc)
    }

    InstructionSet(output, statement, pc)
  }

  def compilePush(statement: ParsedStatement): String = {
    val index = statement.arg2.get
    statement.arg1 match {
      case Some(MemorySegment.constant) => push constant index
      case Some(MemorySegment.static)   => push static("STATIC", index)
      case Some(MemorySegment.local)    => push local index
      case Some(MemorySegment.argument) => push argument index
      case Some(MemorySegment._this)    => push _this index
      case Some(MemorySegment.that)     => push that index
      case Some(MemorySegment.temp)     => push temp index
      case Some(MemorySegment.pointer)  => push pointer index
    }
  }

  def compilePop(statement: ParsedStatement): String = {
    val index = statement.arg2.get
    statement.arg1 match {
      case Some(MemorySegment.static)   => pop static("STATIC", index)
      case Some(MemorySegment.local)    => pop local index
      case Some(MemorySegment.argument) => pop argument index
      case Some(MemorySegment._this)    => pop _this index
      case Some(MemorySegment.that)     => pop that index
      case Some(MemorySegment.temp)     => pop temp index
      case Some(MemorySegment.pointer)  => pop pointer index
    }
  }

  def compileArithmetic(statement: ParsedStatement, pc: Long): String = {
    statement.command match {
      case ArithmeticCommandTypes.Add => Arithmetic.add
      case ArithmeticCommandTypes.Sub => Arithmetic.sub
      case ArithmeticCommandTypes.Neg => Arithmetic.neg
      case ArithmeticCommandTypes.And => Arithmetic.and
      case ArithmeticCommandTypes.Or => Arithmetic.or
      case ArithmeticCommandTypes.Not => Arithmetic.not
      case ArithmeticCommandTypes.Eq => Arithmetic.eq(pc)
      case ArithmeticCommandTypes.Lt => Arithmetic.lt(pc)
      case ArithmeticCommandTypes.Gt => Arithmetic.gt(pc)
    }
  }
}
