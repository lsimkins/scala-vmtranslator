package com.bitparadigm


object RAMAddresses {
  case class AddressRange(start: Long, end: Long)

  // Pointers
  // Assembly language spc allows for these references to memory addresses
  val SP = "SP" // 0
  val LCL = "LCL" // 1
  val ARG = "ARG" // 2
  val THIS = "THIS" // 3
  val THAT = "THAT" // 4

  val temp = AddressRange(5, 12)
  val frame = 5
  val fnReturnAddress = 6
  val savedMemoryAddress = 13
  val savedMemoryAddress2 = 14
  val static = AddressRange(16, 255)
  val stack = AddressRange(256, 2047)
  val heap = AddressRange(2048, 16383)
  val pointer = AddressRange(3, 4)
  val io = AddressRange(16384, 24575)
}
