package com.spkd.cricket.model

import com.spkd.cricket.type.BatsmanType
import com.spkd.cricket.type.BowlerType
import com.spkd.cricket.type.PlayerRole
data class Player(
    val name: String,
    val role: PlayerRole,
    val battingSkill: Double,
    val bowlingAccuracy: Double,
    val bowlingSpeed: Double,
    val batsmanType: BatsmanType? = null,
    val bowlerType: BowlerType? = null
)