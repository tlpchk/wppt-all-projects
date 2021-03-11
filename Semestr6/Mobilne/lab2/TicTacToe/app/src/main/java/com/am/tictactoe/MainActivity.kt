package com.am.tictactoe

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

private const val player1 = "❌"
private const val player2 = "〇"

class MainActivity : AppCompatActivity() {

    lateinit var actualPlayer: String
    var gameBoard = Array(5){ arrayOfNulls<String>(5)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actualPlayer = player1
        playerView.text = actualPlayer
        createCells()
    }

    private fun createCells() {
        var id = 1
        for (i in 1..5) {
            for (j in 0..4) {
                val cell = Cell(this,id++,i,j)
                cell.setOnClickListener{ onClick(it) }
                grid.addView(cell)
            }
        }
    }

    @SuppressLint("ResourceType")
    fun onClick(view: View){
        val clickedCell = findViewById<Cell>(view.id)
        when (actualPlayer){
            player1 -> {
                clickedCell.text = player1
                clickedCell.setTextColor(Color.RED)
                storeToGameTable(player1,view.id)
            }
            player2 -> {
                clickedCell.text = player2
                clickedCell.setTextColor(Color.BLUE)
                storeToGameTable(player2,view.id)
            }
        }
        clickedCell.isClickable = false

        if(winningMove(view.id)){
            endOfGame(actualPlayer)
        }
        if(fullGameBoard()){
            endOfGame(null)
        }

        nextPlayer()
    }

    private fun fullGameBoard(): Boolean {
        for (row in gameBoard){
            for (cell in row){
                if (cell == null) {
                    return false
                }
            }
        }
        return true
    }

    private fun winningMove(lastClicked: Int): Boolean {
        val i = idToCoords(lastClicked).first
        val j = idToCoords(lastClicked).second
        val player = findViewById<Cell>(lastClicked).text.toString()

        var winOnDiagonal = checkDiagonals(player)
        val winOnRow = checkRow(player,i)
        val winOnColumn = checkColumn(player,j)

        return winOnDiagonal || winOnRow || winOnColumn
    }

    private fun checkColumn(player: String, col: Int): Boolean {
        for (k in 0..4){
            if(gameBoard[k][col] != player){
                return false
            }
        }
        return true
    }

    private fun checkRow(player: String, row: Int): Boolean {
        for (k in 0..4){
            if(gameBoard[row][k] != player){
                return false
            }
        }
        return true
    }

    private fun checkDiagonals(player: String): Boolean {
        var diagonal1 = true
        var diagonal2 = true
        for (k in 0..4){
            if(gameBoard[k][k] != player){
                diagonal1 = false
            }
        }
        for (k in  0..4){
            if(gameBoard[k][4-k] != player){
                diagonal2 = false
            }
        }
        return diagonal1 || diagonal2
    }

    private fun storeToGameTable(value: String, cellId: Int) {
        val i = idToCoords(cellId).first
        val j = idToCoords(cellId).second
        gameBoard[i][j] = value
    }

    private fun nextPlayer() {
        when (actualPlayer){
            player1 -> actualPlayer = player2
            player2 -> actualPlayer = player1
        }
        playerView.text = actualPlayer
    }

    private fun endOfGame(champion: String?) {
        if(champion != null) {
            Toast.makeText(this, "$champion wins!", Toast.LENGTH_SHORT).show()
            nextPlayer()
        }
        else{
            Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
        }

        newGame()
    }

    private fun newGame() {

        gameBoard = Array(5){ arrayOfNulls<String>(5)}
        for(i in 1..25){
            val cell = findViewById<Cell>(i)
            cell.text = ""
            cell.isClickable = true
        }
    }

    private fun idToCoords(id: Int): Pair<Int, Int> {
        return Pair((id - 1)/5,(id-1)%5)
    }
}
