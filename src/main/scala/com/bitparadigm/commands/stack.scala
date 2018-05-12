package com.bitparadigm.commands

object stack {
  def push(value: String) =
    s"""
       |${stack.assign(value)}
       |${SP++}
    """.stripMargin.trim

  def assign(value: String) =
    s"""
       |${*.SP}
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
