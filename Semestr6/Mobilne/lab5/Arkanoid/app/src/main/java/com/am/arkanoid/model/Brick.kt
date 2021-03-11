package com.am.arkanoid.model

import android.util.Log

class Brick(val x1: Float, val y1: Float,val x2: Float,val y2: Float){
    companion object{
        fun construcktBricks(n: Int, m: Int,canvasWidth: Int, brickHeight: Float,padding: Float) : ArrayList<Brick>{
            val brickWidth = canvasWidth/n - 2*padding

            val bricks = arrayListOf<Brick>()
            var b : Brick
            var x: Float
            var y: Float = padding

            for(i in 0 until m){
                x = padding
                for(j in 0 until n){
                    b = Brick(x1 = x, y1 = y, x2 = x + brickWidth,y2 = y + brickHeight)
                    if(!(400 < x && x < 600 &&  200 < y  && y < 400)) {
                        bricks.add(b)
                    }
                    x += brickWidth + 2*padding
                }
                y += brickHeight + 2*padding
            }
            return bricks
        }
    }
}