package com.bitparadigm.commands

import com.bitparadigm.RAMAddresses

object func {
  def _function(fnName: String, numArgs: Int): String = {
    var str = s"($fnName)"
    if (numArgs > 0) {
      str += "\n" + initLocalVar(numArgs)
    }

    str
  }

  // Note: A slightly more efficient solution would only call top
  // two instructions once.
  private def initLocalVar(num: Int) =
    s"""
       |@0
       |D=A
       |${Range(0, num).map(_ => assignLocalAndInc).mkString("\n")}
     """.stripMargin.trim

  private def assignLocalAndInc = {
    s"""
       |${*SP}
       |M=D
       |${SP++}
     """.stripMargin.trim
  }

  def _return(): String = {
    s"""
       |${saveLCLToFrame()}
       |@5
       |D=D-A
       |A=D
       |D=M
       |@${RAMAddresses.fnReturnAddress}
       |M=D
       |${pop argument 0}
       |@ARG
       |D=M+1
       |@SP
       |M=D
       |// Restore THAT
       |${frame.decrementAndRestoreTo("THAT")}
       |// Restore THIS
       |${frame.decrementAndRestoreTo("THIS")}
       | // Restore ARG
       |${frame.decrementAndRestoreTo("ARG")}
       | // Restore LCL
       |${frame.decrementAndRestoreTo("LCL")}
       | // Return
       |@${RAMAddresses.fnReturnAddress}
       |A=M
       |A;JMP
     """.stripMargin.trim
  }

  private def saveLCLToFrame() = {
    s"""
      |@LCL
      |D=M
      |@${RAMAddresses.frame}
      |M=D
    """.stripMargin.trim
  }

  object frame {
    def -- :String = {
      s"""
         |@${RAMAddresses.frame}
         |M=M-1
       """.stripMargin.trim
    }

    def decrementAndRestoreTo(address: String) = {
      s"""
         |${frame--}
         |A=M
         |D=M
         |@$address
         |M=D
       """.stripMargin.trim
    }
  }
}
