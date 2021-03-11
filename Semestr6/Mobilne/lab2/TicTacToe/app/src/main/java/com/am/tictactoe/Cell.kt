package com.am.tictactoe

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout

class Cell : Button {
    constructor(context: Context?) : super(context)

    constructor(context: Context?,id: Int, row: Int, column: Int) : super(context) {
        this.id = id
        this.layoutParams = createLayoutParams(column,row)
        this.setAutoSizeTextTypeUniformWithConfiguration(1,100,1,TypedValue.COMPLEX_UNIT_DIP)
    }

    private fun createLayoutParams(column: Int, row: Int): ViewGroup.LayoutParams? {
        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(row)
        layoutParams.columnSpec = GridLayout.spec(column, GridLayout.FILL,1f)
        layoutParams.height = 0
        layoutParams.width = 0

        return layoutParams
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}