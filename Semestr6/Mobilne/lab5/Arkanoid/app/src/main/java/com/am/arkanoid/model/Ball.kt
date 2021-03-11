package com.am.arkanoid.model

import android.content.SharedPreferences
import kotlin.math.pow
import kotlin.math.sqrt

class Ball(var centerX : Float, var centerY : Float, val radius: Float, val speed: Float) {
    var dx : Float
    var dy : Float

    init{
        dx = speed / sqrt(2f)
        dy = -dx
    }

    fun update() {
        centerX += dx
        centerY += dy
    }

    fun inHorizontalBoundaries(left: Float, right: Float): Boolean {
        return (centerX in left..right)
    }

    fun inVerticalBoundaries(top: Float, bottom: Float): Boolean {
        return (centerY in top..bottom)
    }

    fun changeHorizontalDirection() {
        dx = -dx
    }

    fun changeVerticalDirection() {
        dy = -dy
    }

    fun hitBrick(brick: Brick): Boolean {
        val closestPoint = closestPoint(brick)
        val closestPointX = closestPoint.first
        val closestPointY = closestPoint.second


        val dist = sqrt((centerX - closestPointX).pow(2) + (centerY - closestPointY).pow(2))

        if (dist <= radius){
            if(closestPointX == centerX){
                changeVerticalDirection()
            } else if(closestPointY == centerY){
                changeHorizontalDirection()
            }

            if(closestPointY != centerY && closestPointX != centerX){
                changeVerticalDirection()
                changeHorizontalDirection()
            }
            return true
        }
        return false
    }

    private fun closestPoint(brick: Brick): Pair<Float,Float> {
        var x = if(dx > 0){
            brick.x1
        }else{
            brick.x2
        }

        var y  = if(dy > 0){
            brick.y1
        }else{
            brick.y2
        }

        if(inHorizontalBoundaries(brick.x1, brick.x2)){
            x = centerX
        }else if(inVerticalBoundaries(brick.y1, brick.y2)){
            y = centerY
        }

        return Pair(x,y)
    }

}