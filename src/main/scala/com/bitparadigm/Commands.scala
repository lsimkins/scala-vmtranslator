package com.bitparadigm

object Commands {
  def saveProgramLocation(pc: Long) = {
    s"""
       |@${pc+4}
       |D=A
       |@${RAMAddresses.savedProgramLocation}
       |M=D
     """.stripMargin.trim
  }

  def returnToProgramLocation() = {
    s"""
      |@${RAMAddresses.savedProgramLocation}
      |A=M
      |0;JMP
    """.stripMargin
  }

  def saveMemoryAddress(from: String = "D") = {
    s"""
       |@${RAMAddresses.savedMemoryAddress}
       |M=$from
     """.stripMargin.trim
  }

  def gotoSavedMemoryAddress() = {
    s"""
       |@${RAMAddresses.savedMemoryAddress}
       |A=M
     """.stripMargin.trim
  }

  object push {
    def constant(const: Long) =
    s"""
      |${D.assign(const)}
      |${stack.push("D")}
    """.stripMargin.trim

    def local(idx: Long) =
    s"""
       |${moveToSegment.LCL(idx)}
       |D=M
       |${stack.pushD}
     """.stripMargin.trim

    def argument(idx: Long) =
      s"""
         |${moveToSegment.ARG(idx)}
         |D=M
         |${stack.pushD}
     """.stripMargin.trim

    def _this(idx: Long) =
      s"""
         |${moveToSegment.THIS(idx)}
         |D=M
         |${stack.pushD}
     """.stripMargin.trim

    def that(idx: Long) =
      s"""
         |${moveToSegment.THAT(idx)}
         |D=M
         |${stack.pushD}
     """.stripMargin.trim

    def pointer(idx: Long) =
      s"""
         |${moveToSegment.pointer(idx)}
         |D=M
         |${stack.pushD}
     """.stripMargin.trim

    def static(namespace: String, idx: Long) =
    s"""
       |@$namespace.$idx
       |D=M
       |${stack.pushD}
     """.stripMargin.trim

    def temp(idx: Long) =
    s"""
       |${moveToSegment.TEMP(idx)}
       |D=M
       |${stack.pushD}
     """.stripMargin.trim
  }

  object pop {
    def local(idx: Long) =
    s"""
       |${moveToSegment.LCL(idx)}
       |${popToCurrentAddress}
     """.stripMargin.trim

    def argument(idx: Long) =
      s"""
         |${moveToSegment.ARG(idx)}
         |${popToCurrentAddress}
     """.stripMargin.trim

    def _this(idx: Long) =
      s"""
         |${moveToSegment.THIS(idx)}
         |${popToCurrentAddress}
     """.stripMargin.trim

    def that(idx: Long) =
      s"""
         |${moveToSegment.THAT(idx)}
         |${popToCurrentAddress}
     """.stripMargin.trim

    def pointer(idx: Long) =
      s"""
         |${moveToSegment.pointer(idx)}
         |${popToCurrentAddress}
     """.stripMargin.trim

    def temp(idx: Long) =
      s"""
         |${stack.popD}
         |${moveToSegment.TEMP(idx)}
         |M=D
     """.stripMargin.trim

    private val popToCurrentAddress =
      s"""
        |D=A
        |${saveMemoryAddress("D")}
        |${stack.popD}
        |${gotoSavedMemoryAddress}
        |M=D
      """.stripMargin.trim

    def static(namespace: String, idx: Long) =
    s"""
       |${stack.popD}
       |@$namespace.$idx
       |M=D
     """.stripMargin.trim
  }

  object stack {
    def push(value: String) =
    s"""
      |${stack.assign(value)}
      |${SP++}
    """.stripMargin.trim

    def assign(value: String) =
    s"""
       |${moveToSegment SP}
       |M=${value}
    """.stripMargin.trim

    def popD() =
    s"""
      |${SP--}
      |@SP
      |A=M
      |D=M
    """.stripMargin.trim

    def pushD() =
    s"""
      |${stack.push("D")}
    """.stripMargin.trim

    def addD() =
    """
      |@SP
      |A=M
      |M=M+D
    """.stripMargin.trim

    def subD() =
    """
      |@SP
      |A=M
      |M=M-D
    """.stripMargin.trim
  }

  object SP {
    val ++ =
    """
      |@SP
      |M=M+1
    """.stripMargin.trim

    val -- =
    """
      |@SP
      |M=M-1
    """.stripMargin.trim
  }

  object moveToSegment {
    def LCL: String = moveToSegment(RAMAddresses.LCL)
    def THIS: String = moveToSegment(RAMAddresses.THIS)
    def THAT: String = moveToSegment(RAMAddresses.THAT)
    def SP: String = moveToSegment(RAMAddresses.SP)

    def LCL(idx: Long): String =
      plusIndex(RAMAddresses.LCL, idx)

    def pointer(idx: Long): String =
      s"@${RAMAddresses.pointer.start + idx}"

    def THIS(idx: Long): String =
      plusIndex(RAMAddresses.THIS, idx)

    def THAT(idx: Long): String =
      plusIndex(RAMAddresses.THAT, idx)

    def ARG(idx: Long): String =
      plusIndex(RAMAddresses.ARG, idx)

    def TEMP(idx: Long): String =
      s"@${RAMAddresses.temp.start + idx}"

    def plusIndex(segment: String, idx: Long): String = {
      s"""
         |@${segment}
         |D=M
         |@${idx}
         |A=D+A
     """.stripMargin.trim
    }

    def plusIndex(segment: Long, idx: Long): String = {
      s"""
         |@${segment}
         |D=M
         |@${idx}
         |A=D+A
     """.stripMargin.trim
    }
  }

  def moveToSegment(address: String): String = {
    s"""
       |@${address}
       |A=M
    """.stripMargin.trim
  }

  def moveToSegment(address: Long): String = {
    s"""
       |@${address}
       |A=M
    """.stripMargin.trim
  }

  object D {
    def assign(value: Long) =
    s"""
      |@${value}
      |D=A
    """.stripMargin.trim
  }

  object Arithmetic {
    val add =
    s"""
      |${stack.popD}
      |${SP--}
      |${stack.addD}
      |${SP++}
    """.stripMargin.trim

    val sub =
    s"""
      |${stack.popD}
      |${SP--}
      |${stack.subD}
      |${SP++}
    """.stripMargin.trim

    val and =
    s"""
      |${stack.popD}
      |${SP--}
      |${moveToSegment.SP}
      |M=M&D
      |${SP++}
    """.stripMargin.trim

    val or =
    s"""
      |${stack.popD}
      |${SP--}
      |${moveToSegment SP}
      |M=M|D
      |${SP++}
    """.stripMargin.trim

    val not =
    s"""
      |${SP--}
      |${moveToSegment SP}
      |M=!M
      |${SP++}
    """.stripMargin.trim

    val neg =
    s"""
      |${SP--}
      |${moveToSegment SP}
      |M=-M
      |${SP++}
     """.stripMargin

    def eq(pc: Long) =
    s"""
      |${saveProgramLocation(pc+3)}
      |@EQ
      |0;JMP
    """.stripMargin.trim

    def lt(pc: Long) =
    s"""
      |${saveProgramLocation(pc+3)}
      |@LT
      |0;JMP
    """.stripMargin.trim

    def gt(pc: Long) =
    s"""
      |${saveProgramLocation(pc+3)}
      |@GT
      |0;JMP
    """.stripMargin.trim
  }

  object SubRoutine {
    val eq =
    s"""
      |(EQ)
      |${stack.popD}
      |${SP--}
      |${stack.subD}
      |D=M
      |@EQ2
      |D;JEQ
      |${moveToSegment SP}
      |M=0
      |${SP++}
      |${returnToProgramLocation}
      |(EQ2)
      |${moveToSegment SP}
      |M=-1
      |${SP++}
      |${returnToProgramLocation}
    """.stripMargin.trim

    val lt =
    s"""
     |(LT)
     |${stack.popD}
     |${SP--}
     |${stack.subD}
     |D=M
     |@LT2
     |D;JGE
     |${moveToSegment SP}
     |M=-1
     |${SP++}
     |${returnToProgramLocation}
     |(LT2)
     |${moveToSegment SP}
     |M=0
     |${SP++}
     |${returnToProgramLocation}
    """.stripMargin.trim

    val gt =
    s"""
     |(GT)
     |${stack.popD}
     |${SP--}
     |${stack.subD}
     |D=M
     |@LT2
     |D;JLE
     |${moveToSegment SP}
     |M=-1
     |${SP++}
     |${returnToProgramLocation}
     |(LT2)
     |${moveToSegment SP}
     |M=0
     |${SP++}
     |${returnToProgramLocation}
    """.stripMargin.trim
  }

  def init =
  s"""
    |@${RAMAddresses.stack.start}
    |D=A
    |@SP
    |M=D
   """.stripMargin.trim

  object end {
    val label =
      """
        |(END)
        |D=0
      """.stripMargin.trim
    val goto =
      """
        |@END
        |0;JMP
      """.stripMargin.trim
  }
}
