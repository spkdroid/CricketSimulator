package com.spkd.cricket.simulator

import com.spkd.cricket.model.Player
import com.spkd.cricket.db.NameDatabase
import com.spkd.cricket.type.BatsmanType
import com.spkd.cricket.type.BowlerType
import com.spkd.cricket.type.PlayerRole
import kotlin.random.Random

class CricketGame(private val oversPerInning: Int, private val playersPerTeam: Int) {
    private val teamA = mutableListOf<Player>()
    private val teamB = mutableListOf<Player>()
    private var currentPlayer: Player? = null
    private var currentOver = 1
    private var currentBall = 1
    private var totalScoreTeamA = 0
    private var totalScoreTeamB = 0
    private var totalWicketsTeamA = 0
    private var totalWicketsTeamB = 0
    private var inning = 1

    init {
        initializeTeams()
        currentPlayer = teamA.first()
    }

    private fun initializeTeams() {
        // Initialize teams with players
        // You can customize player attributes as needed
        repeat(playersPerTeam) {
            teamA.add(Player(
                NameDatabase.getRandomBatsmanName(),
                if (it % 2 == 0) PlayerRole.BATSMAN else PlayerRole.BOWLER,
                Random.nextDouble(20.0, 50.0),
                Random.nextDouble(20.0, 50.0),
                Random.nextDouble(120.0, 150.0), // Bowling speed in km/h
                batsmanType = when {
                    it % 3 == 0 -> BatsmanType.OPENER
                    it % 3 == 1 -> BatsmanType.MIDDLE_ORDER
                    else -> BatsmanType.FINISHER
                }
            ))
            teamB.add(Player(
                NameDatabase.getRandomBowlerName(),
                if (it % 2 == 0) PlayerRole.BATSMAN else PlayerRole.BOWLER,
                Random.nextDouble(20.0, 50.0),
                Random.nextDouble(20.0, 50.0),
                Random.nextDouble(120.0, 150.0), // Bowling speed in km/h
                bowlerType = when {
                    it % 4 == 0 -> BowlerType.SEAM
                    it % 4 == 1 -> BowlerType.OFF_SPIN
                    it % 4 == 2 -> BowlerType.LEG_SPIN
                    else -> BowlerType.SLOW_LEFT_ARM
                }
            ))
        }
    }

    private fun switchTeams() {
        if (inning == 1) {
            currentPlayer = teamB.first()
        } else {
            println("Inning $inning ended.")
            currentPlayer = null
        }
        inning++
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer in teamA) {
            teamA[(teamA.indexOf(currentPlayer!!) + 1) % playersPerTeam]
        } else {
            teamB[(teamB.indexOf(currentPlayer!!) + 1) % playersPerTeam]
        }
    }

    private fun bowl() {
        val runs = Random.nextInt(0, 7) // Simulate runs from 0 to 6
        val battingOutcome = Random.nextDouble(0.0, 1.0)

        println("${currentPlayer!!.name} scores $runs run(s) in inning $inning, over $currentOver, ball $currentBall")

        if (inning == 1) {
            totalScoreTeamA += if (currentPlayer in teamA) runs else 0
        } else {
            totalScoreTeamB += if (currentPlayer in teamB) runs else 0
        }

        if (runs == 0 || battingOutcome > currentPlayer!!.battingSkill) {
            if (inning == 1) {
                totalWicketsTeamA++
            } else {
                totalWicketsTeamB++
            }
            println("${currentPlayer!!.name} is out!")
            switchPlayer()
        }

        currentBall++
        if (currentBall > 6) {
            currentBall = 1
            currentOver++
            switchPlayer()
        }
    }



    private fun printMatchResult() {
        println("Match Over!")

        println("Team A Score: $totalScoreTeamA/$totalWicketsTeamA in $oversPerInning overs")
        println("Team B Score: $totalScoreTeamB/$totalWicketsTeamB in $oversPerInning overs")

        when {
            totalScoreTeamA > totalScoreTeamB -> println("Team A wins!")
            totalScoreTeamA < totalScoreTeamB -> println("Team B wins!")
            else -> println("It's a tie!")
        }
    }

    fun playGame() {
        println("Cricket Game Started!")
        totalScoreTeamA = 0
        totalScoreTeamB = 0
        totalWicketsTeamA = 0
        totalWicketsTeamB = 0
        // First Inning
        while (inning <= 2) {
            currentOver = 1
            currentBall = 1
            currentPlayer = if (inning == 1) teamA.first() else teamB.first()

            while (currentOver <= oversPerInning && totalWicketsTeamA < playersPerTeam && totalWicketsTeamB < playersPerTeam) {
                println("Current Score: ${if (inning == 1) totalScoreTeamA else totalScoreTeamB}/" +
                        "${if (inning == 1) totalWicketsTeamA else totalWicketsTeamB} " +
                        "in Inning $inning, Over $currentOver")

                print("Press enter to bowl...")
                readLine()

                bowl()
            }

            switchTeams()
        }
        printMatchResult()
    }
}