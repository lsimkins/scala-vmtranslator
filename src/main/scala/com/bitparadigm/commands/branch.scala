package com.bitparadigm.commands

object branch {
  def label(label: String) = s"($label)"

  def ifGoTo(label: String) =
  s"""
    |${SP--}
    |${*SP}
    |D=M
    |@$label
    |D;JNE
  """.stripMargin.trim
}
