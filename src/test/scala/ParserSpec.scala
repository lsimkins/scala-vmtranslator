import java.io.FileNotFoundException

import org.scalatest._
import com.bitparadigm.ProgramParser

import scala.io.Source
import scala.util.Success
import scala.util.Failure

class ParserSpec extends FlatSpec with Matchers {
  "Parser" should "throw a FileNotFoundException when no program file is found" in {
    ProgramParser.fromFile("BasicTest.vm") match {
      case Success(_) => fail()
      case Failure(ex) => assert(ex.isInstanceOf[FileNotFoundException])
    }
  }

  it should "return an instance of a ProgramParser" in {
    val file = getClass.getResource( "BasicTest.vm" ).getPath
    ProgramParser.fromFile(file) match {
      case Failure(ex) => throw ex
      case Success(parser) => assert(parser.isInstanceOf[ProgramParser])
    }
  }

  it must "ignore comments, newlines and empty lines in a program file" in {
    val lines = Iterator(
      "// This is a comment",
      "\n",
      "",
      "push local 5",
      "push constant 12",
      "pop argument 1"
    )

    val commands = Iterator(
      "push local 5",
      "push constant 12",
      "pop argument 1"
    )

    val parser = new ProgramParser(lines)

    assert(parser.rawStatements.toList equals commands.toList)
  }
}