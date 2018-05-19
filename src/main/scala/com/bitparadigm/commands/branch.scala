package com.bitparadigm.commands

object branch {
  def label(label: String) = s"($label)"

  def ifGoTo() =
  s"""
    |${*SP}
    |M;JGT
  """.stripMargin
}
