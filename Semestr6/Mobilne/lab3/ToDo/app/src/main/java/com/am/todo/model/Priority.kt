package com.am.todo.model

import android.graphics.Color
import com.am.todo.R

enum class Priority(val drawableCircle: Int, val textColor: Int){
    VERY_LOW(R.drawable.circle_empty, Color.BLACK),
    LOW(R.drawable.circle_green, Color.BLACK),
    MEDIUM(R.drawable.circle_yellow, Color.BLACK),
    HIGH(R.drawable.circle_red, Color.WHITE),
    CRITICAL(R.drawable.circle_blood, Color.WHITE),
    DEAD(R.drawable.circle_black, Color.WHITE),
}