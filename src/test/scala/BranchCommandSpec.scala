import org.scalatest.{FlatSpec, Matchers}
import com.bitparadigm.commands._

class BranchCommandSpec extends FlatSpec with Matchers {
  "Branch Commands" must "push constant 5" in {
    val result = label("SOME_LABEL")
    val expected = "(SOME_LABEL)"

    assert(result == expected)
  }

  it must "goto a label if top of stack is not equal" in {
    val result = ifGoTo("SOME_LABEL")
    val expected =
      """
        |@SP
        |M=M-1
        |@SP
        |A=M
        |D=M
        |@SOME_LABEL
        |D;JNE
      """.stripMargin.trim

    assert(result == expected)
  }
}

