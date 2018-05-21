package com.bitparadigm

import CommandType._
import commands._
import commands.arithmetic._

class InstructionTranslator(statements: Seq[ParsedStatement], bootstrap: Boolean = true) {
  private val headers = {
    if (bootstrap) {
      commands.bootstrap
    } else {
      ""
    }
  }

  private val footers = Seq(
    end.goto,
    subroutines.eq,
    subroutines.lt,
    subroutines.gt,
    end.label
  ).mkString("\n\n")

  private lazy val headersWithComments =
    s"""
       |// Headers
       |$headers
    """.stripMargin.trim

  private lazy val footersWithComments =
    s"""
       |// Footers
       |$footers
     """.stripMargin.trim

  private val pcStart = headers.lines.length

  def translate(): String = {
    val body = translate(statements, pcStart)
      .map(_.output)
      .mkString("\n")

    List(headers, body, footers).mkString("\n\n")
  }

  def translateWithComments(): String = {
    val body = translate(statements, pcStart)
      .map(s => s.outputWithComment(Some(s"PC: ${s.pc.toString}")))
      .mkString("\n\n")

    List(headersWithComments, body, footersWithComments).mkString("\n\n")
  }

  def translate(statements: Seq[ParsedStatement], pc: Long = 0): Seq[TranslatedStatement] = {
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
      case Function => function(statement.arg1.get, statement.arg2.get.toInt)
      case Return => _return
      case Call => call(statement.arg1.get, statement.arg2.get.toInt, pc)
      case _ => translateArithmetic(statement, pc)
    }

    TranslatedStatement(output, statement, pc)
  }

  def translatePush(statement: ParsedStatement): String = {
    val index = statement.arg2.get
    statement.arg1 match {
      case Some(MemorySegment.constant) => push constant index
      case Some(MemorySegment.static)   => push static(statement.context, index)
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
      case Some(MemorySegment.static)   => pop static(statement.context, index)
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
