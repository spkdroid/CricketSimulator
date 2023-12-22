package com.spkd.cricket.simulator

import com.spkd.cricket.type.TossResult
import kotlin.random.Random

class CoinToss {
    fun performToss(): TossResult {
        return if (Random.nextBoolean()) TossResult.HEADS else TossResult.TAILS
    }
}