package com.am.arkanoid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.am.arkanoid.model.Ball
import com.am.arkanoid.model.Brick
import com.am.arkanoid.model.Platform

class GameView(context: Context, attributeSet: AttributeSet)
    : SurfaceView(context, attributeSet), SurfaceHolder.Callback {

    private var thread: GameThread

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        thread.saveState(context)
        thread.setRunning(false)
        thread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        thread = GameThread(holder!!, this)
        thread.setSurfaceSize(width, height)
        thread.restoreState(context)
        thread.setRunning(true)
        thread.start()
    }


    fun draw(canvas: Canvas?,
             ball : Ball,
             bricks: ArrayList<Brick>,
             platform: Platform) {

        super.draw(canvas)

        val paint = Paint()
        paint.setARGB(255, 255, 0, 0)

        for (b in bricks) {
            canvas?.drawRect(RectF(b.x1, b.y1, b.x2, b.y2), paint)
        }
        canvas?.drawRect(RectF(platform.x, platform.y, platform.x + platform.width, platform.y + platform.height), paint)

        paint.setARGB(255,255,255,255)
        canvas?.drawOval(RectF(ball.centerX - ball.radius,ball.centerY - ball.radius,
            ball.centerX + ball.radius, ball.centerY + ball.radius), paint)
        }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        thread.movePlatform(event.x.toInt())
        return true
    }
}