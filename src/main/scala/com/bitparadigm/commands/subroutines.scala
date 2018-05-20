package com.bitparadigm.commands

import com.bitparadigm.RAMAddresses

object subroutines {
  val eq =
    s"""
       |(EQ)
       |${stack.popD}
       |${SP--}
       |${stack.subD}
       |D=M
       |@EQ2
       |D;JEQ
       |${*SP}
       |M=0
       |${SP++}
       |${returnToProgramLocation}
       |(EQ2)
       |${*(RAMAddresses.SP)}
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
       |${*SP}
       |M=-1
       |${SP++}
       |${returnToProgramLocation}
       |(LT2)
       |${*(RAMAddresses.SP)}
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
       |@GT2
       |D;JLE
       |${*SP}
       |M=-1
       |${SP++}
       |${returnToProgramLocation}
       |(GT2)
       |${*SP}
       |M=0
       |${SP++}
       |${returnToProgramLocation}
    """.stripMargin.trim
}
