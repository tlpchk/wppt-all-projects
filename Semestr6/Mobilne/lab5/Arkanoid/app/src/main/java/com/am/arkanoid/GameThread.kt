package com.am.arkanoid


import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import com.am.arkanoid.model.Ball
import com.am.arkanoid.model.Brick
import com.am.arkanoid.model.Model
import com.am.arkanoid.model.Platform
import com.google.gson.Gson
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class GameThread(private val surfaceHolder: SurfaceHolder,
                 private val gameView: GameView) : Thread() {

    private val PREFS_NAME = "model"

    private var canvasWidth : Int = 0
    private var canvasHeight : Int = 0

    private var model = Model()
    private val n = 10
    private val m = 15
    private val brickHeight = 40f
    private val brickPadding = 5f
    private val platformWidth = 250f

    private var running : Boolean = false
    private val targetFPS = 50
    private var canvas: Canvas? = null

    fun setRunning(isRunning : Boolean) {
        this.running = isRunning
    }

    fun setSurfaceSize(width: Int, height: Int) {
        canvasWidth = width
        canvasHeight = height
    }

    private fun constructBricks() {
        model.bricks = Brick.construcktBricks(n,m,canvasWidth,brickHeight,brickPadding)
    }

    private fun constructPlatform() {
        val x = (canvasWidth - platformWidth)/2
        val y = canvasHeight - brickHeight - brickPadding
        model.platform = Platform(x,y,platformWidth,brickHeight)
    }

    private fun constructBall() {
        val radius = 20f
        val centerX = model.platform.x + model.platform.width/2
        val centerY = model.platform.y - radius
        val speed = 14f

        model.ball = Ball(centerX,centerY,radius,speed)
    }

    private fun update(){
        model.ball.update()

        for(b in model.bricks){
            if(model.ball.hitBrick(b)) {
                model.bricks.remove(b)
                break
            }
        }

        if (! model.ball.inHorizontalBoundaries(0 + model.ball.radius,canvasWidth - model.ball.radius)) {
            model.ball.changeHorizontalDirection()
        }

        if(model.ball.centerY < model.ball.radius){
            model.ball.changeVerticalDirection()
        }
        if (model.ball.centerY > canvasHeight + model.ball.radius){
            constructBall()
            constructBricks()
            constructPlatform()
        }

        if(model.ball.inHorizontalBoundaries(model.platform.x, model.platform.x + model.platform.width)
            && model.ball.inVerticalBoundaries(model.platform.y - model.ball.radius,
                                                model.platform.y + model.platform.height/2)
            && model.ball.dy > 0
        ) {
            model.ball.changeVerticalDirection()
            model.ball.dx = (model.ball.dx/abs(model.ball.dx)) * (5 + Math.random() * (12 - 5)).toFloat()
            model.ball.dy = - sqrt(model.ball.speed.pow(2) - model.ball.dx.pow(2))
        }
    }

    fun movePlatform(x: Int){
        synchronized(model.platform) {
            if (platformWidth / 2 <= x && x <= canvasWidth - platformWidth / 2) {
                model.platform.x = x - platformWidth / 2
            }
        }
    }

    override fun run() {
        var startTime : Long
        var timeMillis : Long
        var waitTime : Long
        val targetTime = (1000 / targetFPS).toLong()

        while(running) {
            startTime = System.nanoTime()
            canvas = null

            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    update()
                    gameView.draw(canvas,model.ball,model.bricks,model.platform)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000

            waitTime = targetTime - timeMillis

            try {
                sleep(waitTime)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun restoreState(context: Context){
        val gson = Gson()
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
        val json = sharedPrefs.getString("Model","")
        if(json == "") {
            constructBricks()
            constructPlatform()
            constructBall()
        }else{
            model = gson.fromJson(json, Model::class.java)
        }

    }


    fun saveState(context: Context) {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(model)
        editor.putString("Model",json)
        editor.commit()
    }

}