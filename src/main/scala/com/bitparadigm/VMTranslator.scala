package com.bitparadigm

import java.io.{File, PrintWriter}

import scala.io.Source

/**
  * Presently, this implementation
  *   1) pulls everything into memory
  *   2) Parses & translates it
  *   3) Writes output to final file
  *
  * A solution that was concerned about memory usage
  * would translate line-by-line and write to a file
  * buffer. Since memory is not going to be a concern
  * for coursework, everything is simply pulled into
  * memory for simplicity.
  */
object VMTranslator extends App {
  if (args.size <= 0) {
    println("Please specify a file or folder")
    System.exit(0)
  }

  val file = new File(args(0))

  if (file.isFile) {
    processFile(file)
  } else if (file.isDirectory) {
    processFolder(file)
  } else {
    println("Supplied path is not a file or folder")
  }

  def processFile(file: File): Unit = {
    val statements = parseFile(file)
    val output = new InstructionTranslator(statements, false).translateWithComments()
    val path = file.getParent
    val basename = file.getName.split("\\.").head

    val outPath = s"$path/$basename.asm"
    writeOutput(output, outPath)
    println(s"Translation successful, written to $outPath")
  }

  def parseFile(file: File): Seq[ParsedStatement] = {
    val lines = Source.fromFile(file).getLines()
    val basename = file.getName.split("\\.").head
    new ProgramParser(lines, basename).statements.toSeq
  }

  def processFolder(folder: File): Unit = {
    val statements = parseFolder(folder)
    val output = new InstructionTranslator(statements, true)
      .translateWithComments()

    val outPath = s"${file.getPath}/${file.getName}.asm"
    writeOutput(output, outPath)
    println(s"Translation successful, written to $outPath")
  }

  def parseFolder(folder: File): Seq[ParsedStatement] = {
    folder
      .listFiles()
      .filter(isVMFile)
      .map(parseFile)
      .reduce((lines1, lines2) => lines1 ++ lines2)
  }

  def writeOutput(output: String, file: String) = {
    new PrintWriter(file) { write(output); close }
  }

  def isVMFile(file: File): Boolean = {
    file.getName.split("\\.").last == "vm"
  }
}

