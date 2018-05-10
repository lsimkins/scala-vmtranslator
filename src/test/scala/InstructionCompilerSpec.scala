import com.bitparadigm.{InstructionCompiler, Commands, ProgramParser}
import org.scalatest.{FlatSpec, Matchers}
import Commands._
import Commands.Arithmetic._

import scala.io.Source
import java.io.PrintWriter

class InstructionCompilerSpec extends FlatSpec with Matchers {
  private val resourceFolder = getClass.getResource("").getPath

  def writeOutput(output: String, file: String) = {
    new PrintWriter(resourceFolder + file) { write(output); close }
  }

  "CodeWriter" must "Add" in {
    val statements =
      new ProgramParser(Seq(
        "push constant 7",
        "push constant 8",
        "add"
      ).toIterator).statements
    val writer = new InstructionCompiler(statements.toSeq)

    writeOutput(writer.compileWithComments(), "add.asm")
  }

  it must "Sub" in {
    val statements =
      new ProgramParser(Seq(
        "push constant 7",
        "push constant 8",
        "sub",
        "push constant 10",
        "push constant 10",
        "sub",
        "push constant 20",
        "push constant 20",
        "sub"
      ).toIterator).statements
    val writer = new InstructionCompiler(statements.toSeq)

    writeOutput(writer.compileWithComments(), "sub.asm")
  }


  it must "Eq" in {
    val statements =
      new ProgramParser(Seq(
        "push constant 17",
        "push constant 17",
        "eq",
        "push constant 17",
        "push constant 16",
        "eq",
        "push constant 16",
        "push constant 17",
        "eq"
      ).toIterator).statements
    val writer = new InstructionCompiler(statements.toSeq)

    writeOutput(writer.compileWithComments(), "eq.asm")
  }

  it must "Lt" in {
    val statements =
      new ProgramParser(Seq(
        "push constant 7",
        "push constant 8",
        "lt", // -1,
        "push constant 7",
        "push constant 7",
        "lt", // 0,
        "push constant 8",
        "push constant 7",
        "lt" // 0
      ).toIterator).statements
    val writer = new InstructionCompiler(statements.toSeq)

    writeOutput(writer.compileWithComments(), "lt.asm")
  }

  it must "Gt" in {
    val statements =
      new ProgramParser(Seq(
        "push constant 7",
        "push constant 8",
        "gt", // 0,
        "push constant 7",
        "push constant 7",
        "gt", // 0,
        "push constant 8",
        "push constant 7",
        "gt" // -1
      ).toIterator).statements
    val writer = new InstructionCompiler(statements.toSeq)

    writeOutput(writer.compileWithComments(), "gt.asm")
  }

  it must "StackTest.vm" in {
    val statements =
      new ProgramParser(Seq(
        "push constant 17",
        "push constant 17",
        "eq",
        "push constant 17",
        "push constant 16",
        "eq",
        "push constant 16",
        "push constant 17",
        "eq",
        "push constant 892",
        "push constant 891",
        "lt",
        "push constant 891",
        "push constant 892",
        "lt",
        "push constant 891",
        "push constant 891",
        "lt",
        "push constant 32767",
        "push constant 32766",
        "gt",
        "push constant 32766",
        "push constant 32767",
        "gt",
        "push constant 32766",
        "push constant 32766",
        "gt",
        "push constant 57",
        "push constant 31",
        "push constant 53",
        "add",
        "push constant 112",
        "sub",
        "neg",
        "and",
        "push constant 82",
        "or",
        "not"
      ).toIterator).statements
    val writer = new InstructionCompiler(statements.toSeq)

    writeOutput(writer.compileWithComments(), "StackTest.asm")
  }

  it must "SimpleStatic.vm" in {
    val statements =
      new ProgramParser(Seq(
        "push constant 111",
        "push constant 333",
        "push constant 888",
        "pop static 8",
        "pop static 3",
        "pop static 1",
        "push static 3"
      ).toIterator).statements
    val writer = new InstructionCompiler(statements.toSeq)

    writeOutput(writer.compileWithComments(), "SimpleStatic.asm")
  }

  it must "StaticTest.vm" in {
    val statements =
      new ProgramParser(Seq(
        "push constant 111",
        "push constant 333",
        "push constant 888",
        "pop static 8",
        "pop static 3",
        "pop static 1",
        "push static 3",
        "push static 1",
        "sub",
        "push static 8",
        "add"
    ).toIterator).statements
    val writer = new InstructionCompiler(statements.toSeq)

    writeOutput(writer.compileWithComments(), "StaticTest.asm")
  }

  it must "LocalTest.vm" in {
    val statements =
      new ProgramParser(Seq(
        "push constant 111",
        "pop local 1",
        "push constant 222",
        "pop local 2",
        "push constant 333",
        "push local 2"
      ).toIterator).statements
    val writer = new InstructionCompiler(statements.toSeq)

    writeOutput(writer.compileWithComments(), "LocalTest.asm")
  }

  it must "BasicTest.vm" in {
    val statements =
      new ProgramParser(Seq(
        "push constant 10",
        "pop local 0",
        "push constant 21",
        "push constant 22",
        "pop argument 2",
        "pop argument 1",
        "push constant 36",
        "pop this 6",
        "push constant 42",
        "push constant 45",
        "pop that 5",
        "pop that 2",
        "push constant 510",
        "pop temp 6",
        "push local 0",
        "push that 5",
        "add",
        "push argument 1",
        "sub",
        "push this 6",
        "push this 6",
        "add",
        "sub",
        "push temp 6",
        "add"
      ).toIterator).statements
    val writer = new InstructionCompiler(statements.toSeq)

    writeOutput(writer.compileWithComments(), "BasicTest.asm")
  }

  it must "PointerTest.vm" in {
    val statements =
      new ProgramParser(Seq(
        "push constant 3030",
        "pop pointer 0",
        "push constant 3040",
        "pop pointer 1",
        "push constant 32",
        "pop this 2",
        "push constant 46",
        "pop that 6",
        "push pointer 0",
        "push pointer 1",
        "add",
        "push this 2",
        "sub",
        "push that 6",
        "add"
      ).toIterator).statements

    val writer = new InstructionCompiler(statements.toSeq)

    writeOutput(writer.compileWithComments(), "PointerTest.asm")
  }
}
