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
       |${popSegment("THAT")}
       |${popSegment("THIS")}
       |${popSegment("ARG")}
       |${popSegment("LCL")}
       |@${RAMAddresses.fnReturnAddress}
       |A=M
       |A;JMP
     """.stripMargin.trim
  }

  def call(fnName: String, numArgs: Int, pc: Long) = {
    val cmds = s"""
       |${pushSegment("LCL")}
       |${pushSegment("ARG")}
       |${pushSegment("THIS")}
       |${pushSegment("THAT")}
       |@SP // ARG = SP-numArgs-5
       |D=M
       |@5
       |D=D-A
       |@$numArgs
       |D=D-A
       |@ARG
       |M=D
       |@SP // LCL = SP
       |D=M
       |@LCL
       |M=D
       |${goTo(fnName)}
     """.stripMargin.trim

    val returnAddress = pc + cmds.lines.length + pushReturnLineCount
    s"""
       |${pushReturn(returnAddress)}
       |${cmds}
     """.stripMargin.trim
  }

  lazy val pushReturnLineCount = pushReturn(0).lines.length
  private def pushReturn(address: Long): String = {
    s"""
       |@$address
       |D=A
       |@SP
       |A=M
       |${stack.pushD}
       """.stripMargin.trim
  }

  private def pushSegment(segment: String) = {
    s"""
       |@${segment}
       |D=M
       |${stack.pushD}
     """.stripMargin.trim
  }

  def popSegment(address: String) = {
    s"""
       |${frame--}
       |A=M
       |D=M
       |@$address
       |M=D
       """.stripMargin.trim
  }

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
  }
}
