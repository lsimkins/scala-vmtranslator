import org.scalatest.{FlatSpec, Matchers}
import com.bitparadigm.Commands._
import java.io.PrintWriter

import com.bitparadigm.RAMAddresses

class CommandSpec extends FlatSpec with Matchers {
  private val resourceFolder = getClass.getResource("").getPath

  def writeOutput(output: String, file: String) = {
    new PrintWriter(resourceFolder + file) {
      write(output); close
    }
  }

  "Memory Access Commands" must "push constant 5" in {
    val result = push constant 5
    val expected =
      """
        |@5
        |D=A
        |@SP
        |A=M
        |M=D
        |@SP
        |M=M+1
      """.stripMargin.trim

    assert(result == expected)
  }

  it must "push static 5" in {
    val result = push static("SPEC", 5)
    val expected =
      """
        |@SPEC.5
        |D=M
        |@SP
        |A=M
        |M=D
        |@SP
        |M=M+1
      """.stripMargin.trim

    assert(result == expected)
  }

  it must "pop static 2" in {
    val result = pop static("SPEC", 2)
    val expected =
      """
        |@SP
        |M=M-1
        |@SP
        |A=M
        |D=M
        |@SPEC.2
        |M=D
      """.stripMargin.trim

    assert(result == expected)
  }

  it must "push local 1" in {
    val result = push local 1
    val expected =
      """
        |@LCL
        |D=M
        |@1
        |A=D+A
        |D=M
        |@SP
        |A=M
        |M=D
        |@SP
        |M=M+1
      """.stripMargin.trim

    assert(result == expected)
  }

  it must "pop local 3" in {
    val result = pop local 3
    val expected =
      s"""
         |@LCL
         |D=M
         |@3
         |A=D+A
         |D=A
         |@${RAMAddresses.savedMemoryAddress}
         |M=D
         |@SP
         |M=M-1
         |@SP
         |A=M
         |D=M
         |@${RAMAddresses.savedMemoryAddress}
         |A=M
         |M=D
      """.stripMargin.trim

    assert(result == expected)
  }
}
