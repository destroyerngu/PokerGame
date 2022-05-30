package player

import poker.Poker
import util.OFF_LINE
import util.ON_LINE

class Player(var name: String, var id: Int) {
    var money: Int = 2000
    var isAlive: Boolean = ON_LINE
    lateinit var poker: Poker
    var lastBet: Int = 0
    fun payTableMoney(money: Int) {
        this.money -= money
    }

    override fun toString(): String {
        var flag = if (this.isAlive) "√" else "×"
        return "No${id}.$name $${money} $poker $flag"
    }
    // deduct money
    fun deductMoney(count: Int) {
        money -= count
    }
    // clear all money
    fun clearAllMoney(): Int {
        lastBet = money
        money = 0
        return lastBet
    }
    // follow bet
    fun followBetMoney(money: Int): Int {
        this.money -= money
        lastBet = money
        return money
    }

}