package com.am.l1z2

import java.lang.Exception

abstract class Pick {
   abstract var imgResource: Int
   abstract fun compare(opponent: Pick): Int
}

class Rock(override var imgResource: Int) : Pick() {
   override fun compare(opponent: Pick) : Int {
      return when (opponent) {
         is Rock -> 0
         is Paper -> -1
         is Scissors -> 1
         else -> throw Exception("Unknown pick")
      }
   }
}

class Paper(override var imgResource: Int) : Pick() {
   override fun compare(opponent: Pick) : Int {
      return when (opponent) {
         is Paper -> 0
         is Scissors -> -1
         is Rock -> 1
         else -> throw Exception("Unknown pick")
      }
   }
}

class Scissors(override var imgResource: Int) : Pick() {
   override fun compare(opponent: Pick) : Int {
      return when (opponent) {
         is Scissors -> 0
         is Rock -> -1
         is Paper -> 1
         else -> throw Exception("Unknown pick")
      }
   }
}