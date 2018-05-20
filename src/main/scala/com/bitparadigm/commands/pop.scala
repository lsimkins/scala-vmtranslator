package com.bitparadigm.commands

object pop {
  def local(idx: Long) =
    s"""
       |${*LCL(idx)}
       |${popToCurrentAddress}
     """.stripMargin.trim

  def argument(idx: Long) =
    s"""
       |${*ARG(idx)}
       |${popToCurrentAddress}
     """.stripMargin.trim

  def _this(idx: Long) =
    s"""
       |${*THIS(idx)}
       |${popToCurrentAddress}
     """.stripMargin.trim

  def that(idx: Long) =
    s"""
       |${*THAT(idx)}
       |${popToCurrentAddress}
     """.stripMargin.trim

  def pointer(idx: Long) =
    s"""
       |${*pointer(idx)}
       |${popToCurrentAddress}
     """.stripMargin.trim

  def temp(idx: Long) =
    s"""
       |${stack.popD}
       |${*TEMP(idx)}
       |M=D
     """.stripMargin.trim

  private val popToCurrentAddress =
    s"""
       |D=A
       |${saveMemoryAddress("D")}
       |${stack.popD}
       |${goToSavedMemoryAddress}
       |M=D
      """.stripMargin.trim

  def static(namespace: String, idx: Long) =
    s"""
       |${stack.popD}
       |@$namespace.$idx
       |M=D
     """.stripMargin.trim
}
