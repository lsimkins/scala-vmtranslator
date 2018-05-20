import org.scalatest.{FlatSpec, Matchers}
import com.bitparadigm.commands._

class FunctionCommandSpec extends FlatSpec with Matchers {
  "Function Commands" must "translate a function declaration with 2 arguments" in {
    val result = function("new_function", 2)
    val expected =
      """
        |(new_function)
        |@0
        |D=A
        |@SP
        |A=M
        |M=D
        |@SP
        |M=M+1
        |@SP
        |A=M
        |M=D
        |@SP
        |M=M+1
      """.stripMargin.trim

    assert(result == expected)
  }

  it must "translate a function declaration with 0 arguments" in {
    val result = function("new_function", 0)
    val expected = "(new_function)"

    assert(result == expected)
  }
}


