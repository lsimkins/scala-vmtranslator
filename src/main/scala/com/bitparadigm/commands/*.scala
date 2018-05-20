package com.bitparadigm.commands

import com.bitparadigm.RAMAddresses

// Dereference
object * {
  def apply(address: String): String = {
    s"""
       |@${address}
       |A=M
    """.stripMargin.trim
  }

  def apply(address: Long): String = {
    s"""
       |@${address}
       |A=M
    """.stripMargin.trim
  }

  def ARG: String = *(RAMAddresses.ARG)
  def LCL: String = *(RAMAddresses.LCL)
  def THIS: String = *(RAMAddresses.THIS)
  def THAT: String = *(RAMAddresses.THAT)
  def SP: String = *(RAMAddresses.SP)

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
