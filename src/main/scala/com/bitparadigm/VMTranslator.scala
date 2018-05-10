package com.bitparadigm

import java.io.{File, PrintWriter}

import scala.io.Source

object VMTranslator extends App {
  if (args.size <= 0) {
    println("Please specify a file")
    System.exit(0)
  }

  val file = new File(args(0))
  val path = file.getParent
  val basename = file.getName.split("\\.").head

  val lines = Source.fromFile(file).getLines()
  val statements = new ProgramParser(lines).statements.toSeq
  val output = new InstructionCompiler(statements).compileWithComments()

  writeOutput(output, s"$path/$basename.asm")

  def writeOutput(output: String, file: String) = {
    new PrintWriter(file) { write(output); close }
  }
}
