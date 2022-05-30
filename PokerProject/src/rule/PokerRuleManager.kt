package rule

import game.HappyPokerGame
import player.Player
import poker.Poker

class PokerRuleManager {
    companion object {
        fun compareTwpPokers(p1: Poker, p2: Poker): Boolean {
            return if (p1.pokerNumber.tag == p2.pokerNumber.tag) {
                p1.pokerSuit.tag > p2.pokerSuit.tag
            } else {
                p1.pokerNumber.tag > p2.pokerNumber.tag
            }
        }

        fun findMaxPlayer(playerList: List<Player>): Player {
            val happyPokerGame = HappyPokerGame.sharedHappyPokerGame
            var max = playerList[happyPokerGame.getCurrentPlayerIndex()]
            // use list to store player whose status is ON_LINE
            var list: ArrayList<Player> = arrayListOf()
            playerList.forEach(){
                if (it.isAlive) {
                    list.add(it)
                }
            }
            for (player in list) {
                // need to find the player who is on-line
                if (!compareTwpPokers(max.poker, player.poker)) {
                    max = player
                }
            }
            return max
        }
    }
}