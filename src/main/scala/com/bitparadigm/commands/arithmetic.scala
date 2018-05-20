package com.bitparadigm.commands

object arithmetic {
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
       |${*.SP}
       |M=M&D
       |${SP++}
    """.stripMargin.trim

  val or =
    s"""
       |${stack.popD}
       |${SP--}
       |${* SP}
       |M=M|D
       |${SP++}
    """.stripMargin.trim

  val not =
    s"""
       |${SP--}
       |${* SP}
       |M=!M
       |${SP++}
    """.stripMargin.trim

  val neg =
    s"""
       |${SP--}
       |${* SP}
       |M=-M
       |${SP++}
     """.stripMargin

  def eq(pc: Long) =
    s"""
       |${saveProgramLocation(pc+2)}
       |@EQ
       |0;JMP
    """.stripMargin.trim

  def lt(pc: Long) =
    s"""
       |${saveProgramLocation(pc+2)}
       |@LT
       |0;JMP
    """.stripMargin.trim

  def gt(pc: Long) =
    s"""
       |${saveProgramLocation(pc+2)}
       |@GT
       |0;JMP
    """.stripMargin.trim
}
