package com.bitparadigm.commands

object push {
  def constant(const: Long) =
    s"""
       |@$const
       |D=A
       |${stack.push("D")}
    """.stripMargin.trim

  def local(idx: Long) =
    s"""
       |${*LCL(idx)}
       |D=M
       |${stack.pushD}
     """.stripMargin.trim

  def argument(idx: Long) =
    s"""
       |${*ARG(idx)}
       |D=M
       |${stack.pushD}
     """.stripMargin.trim

  def _this(idx: Long) =
    s"""
       |${*THIS(idx)}
       |D=M
       |${stack.pushD}
     """.stripMargin.trim

  def that(idx: Long) =
    s"""
       |${*THAT(idx)}
       |D=M
       |${stack.pushD}
     """.stripMargin.trim

  def pointer(idx: Long) =
    s"""
       |${*pointer(idx)}
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
       |${*TEMP(idx)}
       |D=M
       |${stack.pushD}
     """.stripMargin.trim
}
