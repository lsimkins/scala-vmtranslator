package com.bitparadigm

final case class TranslationError(
  private val message: String = "",
  private val cause: Throwable = None.orNull
) extends Exception(message, cause)
