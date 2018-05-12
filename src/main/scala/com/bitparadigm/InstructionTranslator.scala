package com.bitparadigm

import CommandType._
import commands._
import commands.arithmetic._
import MemorySegment._

class InstructionTranslator(statements: Seq[ParsedStatement]) {
  lazy val headers = Seq(
    commands.init
  ).mkString("\n\n")

  lazy val footers = Seq(
    end.goto,
    subroutines.eq,
    subroutines.lt,
    subroutines.gt,
    end.label
  ).mkString("\n\n")

  lazy val headersWithComments =
    s"""
      |// Headers
      |${headers}
    """.stripMargin.trim
  lazy val pcStart = -1 + headers.split("\n").size

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

  def compile(statements: Seq[ParsedStatement], pc: Long = -1): Seq[TranslatedStatement] = {
    if (statements.isEmpty) {
      Seq.empty
    } else {
      val instructions = compile(statements.head, pc)
      instructions +: compile(statements.tail, pc + instructions.count)
    }
  }

  def compile(statement: ParsedStatement, pc: Long): TranslatedStatement = {
    val output = statement.command match {
      case Push => compilePush(statement)
      case Pop => compilePop(statement)
      case _ => compileArithmetic(statement, pc)
    }

    TranslatedStatement(output, statement, pc)
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
      case _ => throw TranslatorError("Cannot translate statement", new Throwable(statement.raw))
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
      case _ => throw TranslatorError("Cannot translate statement", new Throwable(statement.raw))
    }
  }

  def compileArithmetic(statement: ParsedStatement, pc: Long): String = {
    statement.command match {
      case ArithmeticCommandTypes.Add => add
      case ArithmeticCommandTypes.Sub => sub
      case ArithmeticCommandTypes.Neg => neg
      case ArithmeticCommandTypes.And => and
      case ArithmeticCommandTypes.Or => or
      case ArithmeticCommandTypes.Not => not
      case ArithmeticCommandTypes.Eq => arithmetic.eq(pc)
      case ArithmeticCommandTypes.Lt => lt(pc)
      case ArithmeticCommandTypes.Gt => gt(pc)
      case _ => throw TranslatorError("Cannot translate statement", new Throwable(statement.raw))
    }
  }
}
