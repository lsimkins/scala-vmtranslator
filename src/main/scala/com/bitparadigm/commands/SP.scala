package com.bitparadigm.commands

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
