package com.am.l1z2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    private val picks: MutableMap<Int,Pick> = mutableMapOf(
        R.id.rockButton to Rock(R.drawable.rock),
        R.id.paperButton to Paper(R.drawable.paper),
        R.id.scissorsButton to Scissors(R.drawable.scissiors)
    )

    private lateinit var playerPick: Pick
    private lateinit var computerPick: Pick

    private lateinit var playerView: ImageView
    private lateinit var computerView: ImageView
    private lateinit var scoreView: TextView

    private var score = 0

    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playerView = findViewById(R.id.playerImageView)
        computerView = findViewById(R.id.computerImageView)
        scoreView = findViewById(R.id.scoreView)
        refreshScore()
    }

    fun onClick(view: View) {
        playerPick = picks[view.id]!!
        computerPick = randomPick()
        playerView.setImageResource(playerPick.imgResource)
        computerView.setImageResource(computerPick.imgResource)
        val result = playerPick.compare(computerPick)
        showToast(result)
        score += result
        refreshScore()
    }


    private fun randomPick(): Pick {
        return picks.entries.elementAt(random.nextInt(picks.size)).value
    }


    private fun showToast(result: Int) {
        when (result) {
            -1 -> Toast.makeText(this, "You lose", Toast.LENGTH_SHORT).show()
             0 -> Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()
             1 -> Toast.makeText(this, "You win", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshScore() {
        scoreView.text = "Score: $score"
    }
}
