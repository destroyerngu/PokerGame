package player

import poker.Poker
import util.OFF_LINE
import util.ON_LINE
import util.PLAYER_NAME_LIST
import kotlin.math.min

class PlayerManager {
    private var playersList = arrayListOf<Player>()
    init {
        for ((i,name) in PLAYER_NAME_LIST.withIndex()) {
            playersList.add(
                Player(name,i+1)
            )
        }
    }
    companion object {
        val sharedPlayerManager: PlayerManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            PlayerManager()
        }
    }

    // justify a play‘money is enough
    fun isMoneyEnough(playerIndex: Int, money: Int): Boolean {
        return playersList[playerIndex].money >= money
    }

    // get a player by index to show its information
    fun getPlayerWithIndex(index: Int) = playersList[index]

    // 督促玩家交底注
    fun playerBottomBet(money: Int) {
        for (player in playersList) {
            player.payTableMoney(money)
        }
    }
    // get the list of players
    fun getPlayerList() = playersList

    // change the status of Player by using its index
    fun changePlayerStatusWithIndex(index: Int,liveStatus: Boolean) {
        playersList[index].isAlive = liveStatus
    }
    // find the next player who is ON_LINE
    fun findNextPlayer(start: Int): Int {
        var index = start
        while (true) {
            // use this can prevent the situation of arrayOutOfIndex
            index %= playersList.size
            if (playersList[index].isAlive == ON_LINE) {
                return index
            }
            index++
        }
    }
    // award winner
    fun awardWinner(money: Int) {
        var player = playersList[findNextPlayer(0)]
        player.money += money
    }
    fun awardWinner(winner: Player, money: Int) {
        winner.money += money
        for (player in playersList) {
            if (player != winner) {
                player.isAlive = OFF_LINE
            }
        }
    }

    fun findMinBetMoney(): Int {
        val minIndex = findNextPlayer(0)
        var minMoney = playersList[minIndex].lastBet
        for (player in playersList) {
            if (player.isAlive == ON_LINE) {
                minMoney = min(minMoney, player.lastBet)
            }
        }
        return minMoney
    }

    fun returnExtraMoney(minMoney: Int): Int {
        var totalMoney = 0
        for (player in playersList) {
            if (player.isAlive == ON_LINE) {
                player.money = player.lastBet - minMoney
                totalMoney += player.money

            }
        }
        return totalMoney
    }


}