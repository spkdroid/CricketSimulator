package com.spkd.cricket.db

class NameDatabase {
    companion object {
        private val batsmanNames = listOf(
            "Sachin", "Virat", "Rohit", "Steve", "Joe", "Kane", "AB", "Kumar", "David", "Chris"
        )

        private val bowlerNames = listOf(
            "James", "Pat", "Jasprit", "Mitchell", "Rashid", "Adil", "Kagiso", "Rabada", "Josh", "Mujeeb"
        )

        fun getRandomBatsmanName(): String = batsmanNames.random()

        fun getRandomBowlerName(): String = bowlerNames.random()
    }
}
