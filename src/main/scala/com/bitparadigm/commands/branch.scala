package com.bitparadigm.commands

object branch {
  def label(label: String) = s"($label)"

  def goTo(label: String): String =
    s"""
       |@$label
       |A;JMP
     """.stripMargin.trim


  def ifGoTo(label: String): String =
  s"""
    |${SP--}
    |${*SP}
    |D=M
    |@$label
    |D;JNE
  """.stripMargin.trim
}
