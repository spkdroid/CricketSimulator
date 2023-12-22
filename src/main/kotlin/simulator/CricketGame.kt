package com.spkd.cricket.simulator

import com.spkd.cricket.model.Player
import com.spkd.cricket.db.NameDatabase
import com.spkd.cricket.model.PlayerSummary
import com.spkd.cricket.type.BatsmanType
import com.spkd.cricket.type.BowlerType
import com.spkd.cricket.type.PlayerRole
import com.spkd.cricket.type.TossResult
import kotlin.random.Random

class CricketGame(private val oversPerInning: Int, private val playersPerTeam: Int) {
    private val coinToss = CoinToss()
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

    private var manOfTheMatch: Player? = null

    private val playerSummaries = mutableListOf<PlayerSummary>()

    private lateinit var battingTeam: MutableList<Player>

    private lateinit var bowlingTeam: MutableList<Player>

    init {
        initializeTeams()
        currentPlayer = teamA.first()
    }

    private fun initializeTeams() {
        // Initialize teams with players
        // You can customize player attributes as needed
        repeat(playersPerTeam) {
            teamA.add(
                Player(
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
                )
            )
            teamB.add(
                Player(
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
                )
            )
        }
    }

    private fun performToss() {
        val tossResult = coinToss.performToss()

        println("Coin Toss Result: ${tossResult.name}")

        if (tossResult == TossResult.HEADS) {
            battingTeam = teamA.toMutableList()
            bowlingTeam = teamB.toMutableList()
        } else {
            battingTeam = teamB.toMutableList()
            bowlingTeam = teamA.toMutableList()
        }

        println("${battingTeam[0].name} won the toss and chose to bat.")
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

    private fun
            bowl() {
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

    private fun printPlayerSummary() {
        println("Player Summary:")
        playerSummaries.forEach {
            println("${it.playerName}: Runs Scored=${it.runsScored}, Wickets Taken=${it.wicketsTaken}")
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

    private fun printTeamPlayers(team: List<Player>, teamName: String) {
        println("$teamName Players:")
        team.forEachIndexed { index, player ->
            val playerType = if (player.role == PlayerRole.BATSMAN) {
                when (player.batsmanType) {
                    BatsmanType.OPENER -> "Opener"
                    BatsmanType.MIDDLE_ORDER -> "Middle Order"
                    BatsmanType.FINISHER -> "Finisher"
                    else -> ""
                }
            } else {
                when (player.bowlerType) {
                    BowlerType.SEAM -> "Seam"
                    BowlerType.OFF_SPIN -> "Off-Spin"
                    BowlerType.LEG_SPIN -> "Leg Spin"
                    BowlerType.SLOW_LEFT_ARM -> "Slow Left Arm"
                    else -> ""
                }
            }

            println("${index + 1}. ${player.name} - ${player.role} ($playerType)")
        }
    }

    private fun calculateManOfTheMatch() {
        // Calculate Man of the Match based on a simple scoring system (you can adjust this as needed)
        val teamAScore =
            teamA.sumBy { if (it.role == PlayerRole.BATSMAN) it.battingSkill.toInt() else it.bowlingAccuracy.toInt() }
        val teamBScore =
            teamB.sumBy { if (it.role == PlayerRole.BATSMAN) it.battingSkill.toInt() else it.bowlingAccuracy.toInt() }

        manOfTheMatch = if (teamAScore > teamBScore) {
            teamA.maxByOrNull { if (it.role == PlayerRole.BATSMAN) it.battingSkill else it.bowlingAccuracy }
        } else {
            teamB.maxByOrNull { if (it.role == PlayerRole.BATSMAN) it.battingSkill else it.bowlingAccuracy }
        }
    }


    private fun printManOfTheMatch() {
        if (manOfTheMatch != null) {
            println("Man of the Match: ${manOfTheMatch!!.name}")
        } else {
            println("No Man of the Match selected.")
        }
    }

    fun playGame() {
        println("Cricket Game Started!")

        // Display team players
        printTeamPlayers(teamA, "Team A")
        printTeamPlayers(teamB, "Team B")

        performToss()
        totalScoreTeamA = 0
        totalScoreTeamB = 0
        totalWicketsTeamA = 0
        totalWicketsTeamB = 0
        // First Inning
        while (inning <= 2) {
            currentOver = 1
            currentBall = 1
            currentPlayer = if (inning == 1) teamA.first() else teamB.first()

            while ((inning == 1 && currentOver <= oversPerInning && totalWicketsTeamA < playersPerTeam) ||
                (inning == 2 && currentOver <= oversPerInning && totalScoreTeamB < totalScoreTeamA && totalWicketsTeamB < playersPerTeam)
            ) {
                println(
                    "Current Score: ${if (inning == 1) totalScoreTeamA else totalScoreTeamB}/" +
                            "${if (inning == 1) totalWicketsTeamA else totalWicketsTeamB} " +
                            "in Inning $inning, Over $currentOver"
                )

                if (inning == 1) {
                    println("Current Score: $totalScoreTeamA/$totalWicketsTeamA in Inning 1, Over $currentOver")
                } else {
                    println("Target Score: $totalScoreTeamA\nCurrent Score: $totalScoreTeamB/$totalWicketsTeamB in Inning 2")
                }
                println("-------------------------------------------------------------------------")
                print("Press enter to bowl...")
                readLine()
                bowl()
            }
            switchTeams()
        }
        printMatchResult()
    }
}