package com.bitparadigm

package object commands {
  def init =
    s"""
       |@${RAMAddresses.stack.start}
       |D=A
       |@SP
       |M=D
   """.stripMargin.trim

  def saveProgramLocation(pc: Long) = {
    s"""
       |@${pc+4}
       |D=A
       |@${RAMAddresses.savedProgramLocation}
       |M=D
     """.stripMargin.trim
  }

  def returnToProgramLocation() = {
    s"""
       |@${RAMAddresses.savedProgramLocation}
       |A=M
       |0;JMP
    """.stripMargin
  }

  def saveMemoryAddress(from: String = "D") = {
    s"""
       |@${RAMAddresses.savedMemoryAddress}
       |M=$from
     """.stripMargin.trim
  }

  def gotoSavedMemoryAddress() = {
    s"""
       |@${RAMAddresses.savedMemoryAddress}
       |A=M
     """.stripMargin.trim
  }

  def moveToSegment(address: String): String = {
    s"""
       |@${address}
       |A=M
    """.stripMargin.trim
  }

  def moveToSegment(address: Long): String = {
    s"""
       |@${address}
       |A=M
    """.stripMargin.trim
  }

  def label(label: String): String = branch.label(label)
  def ifGoTo(label: String): String = branch.ifGoTo(label)
  def goTo(label: String): String = branch.goTo(label)

  object end {
    val label =
      """
        |(END)
        |D=0
      """.stripMargin.trim
    val goto =
      """
        |@END
        |0;JMP
      """.stripMargin.trim
  }
}
