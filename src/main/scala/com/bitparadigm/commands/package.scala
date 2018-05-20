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
       |@${RAMAddresses.savedMemoryAddress2}
       |M=D
     """.stripMargin.trim
  }

  def returnToProgramLocation() = {
    s"""
       |@${RAMAddresses.savedMemoryAddress2}
       |A=M
       |0;JMP
    """.stripMargin
  }

  def saveMemoryAddress(from: String) = {
    s"""
       |@${RAMAddresses.savedMemoryAddress}
       |M=$from
     """.stripMargin.trim
  }

  def goToSavedMemoryAddress() = {
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
  def function(fnName: String, numArgs: Int): String = func._function(fnName, numArgs)
  lazy val _return: String = func._return()

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
