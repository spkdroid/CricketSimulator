package com.spkd.cricket

import com.spkd.cricket.simulator.CricketGame

fun main() {
    val cricketGame = CricketGame(1, 11) // 2 overs per side, 2 players per team
    cricketGame.playGame()
}
