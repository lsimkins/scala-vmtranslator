package com.bitparadigm

import CommandType._
import commands._
import commands.arithmetic._

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

  def translate(): String = {
    val body = translate(statements, pcStart)
      .map(_.output)
      .mkString("\n")

    Seq(headers, body, footers).mkString("\n")
  }

  def translateWithComments(): String = {
    val body = translate(statements, pcStart)
      .map(s => s.outputWithComment(Some(s"PC: ${s.pc.toString}")))
      .mkString("\n\n")

    Seq(headersWithComments, body, footersWithComments).mkString("\n\n")
  }

  def translate(statements: Seq[ParsedStatement], pc: Long = -1): Seq[TranslatedStatement] = {
    if (statements.isEmpty) {
      Seq.empty
    } else {
      val instructions = translate(statements.head, pc)
      instructions +: translate(statements.tail, pc + instructions.count)
    }
  }

  def translate(statement: ParsedStatement, pc: Long): TranslatedStatement = {
    val output = statement.command match {
      case Push => translatePush(statement)
      case Pop => translatePop(statement)
      case Label => label(statement.arg1.get)
      case IfGoTo => ifGoTo(statement.arg1.get)
      case GoTo => goTo(statement.arg1.get)
      case _ => translateArithmetic(statement, pc)
    }

    TranslatedStatement(output, statement, pc)
  }

  def translatePush(statement: ParsedStatement): String = {
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
      case _ => throw TranslationError("Cannot translate statement", new Throwable(statement.raw))
    }
  }

  def translatePop(statement: ParsedStatement): String = {
    val index = statement.arg2.get
    statement.arg1 match {
      case Some(MemorySegment.static)   => pop static("STATIC", index)
      case Some(MemorySegment.local)    => pop local index
      case Some(MemorySegment.argument) => pop argument index
      case Some(MemorySegment._this)    => pop _this index
      case Some(MemorySegment.that)     => pop that index
      case Some(MemorySegment.temp)     => pop temp index
      case Some(MemorySegment.pointer)  => pop pointer index
      case _ => throw TranslationError("Cannot translate statement", new Throwable(statement.raw))
    }
  }

  def translateArithmetic(statement: ParsedStatement, pc: Long): String = {
    statement.command match {
      case Add => add
      case Sub => sub
      case Neg => neg
      case And => and
      case Or  => or
      case Not => not
      case Eq  => arithmetic.eq(pc)
      case Lt  => lt(pc)
      case Gt  => gt(pc)
      case _ => throw TranslationError("Cannot translate statement", new Throwable(statement.raw))
    }
  }
}
