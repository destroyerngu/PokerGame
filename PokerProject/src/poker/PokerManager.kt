package poker

import util.POKER_NUMBER_LIST
import util.POKER_SUIT_LIST
import kotlin.random.Random

class PokerManager {
    private var pokerList = arrayListOf<Poker>()
    companion object {
        val sharedPokerManager: PokerManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            PokerManager()
        }
    }
    init {
        for ((i, number) in POKER_NUMBER_LIST.withIndex()) {
            for ((j, suit) in POKER_SUIT_LIST.withIndex()) {
                pokerList.add(
                    Poker(
                        PokerNumber(number, i),
                        PokerSuit(suit, j)
                    )
                )
            }
        }
    }
     fun getOnePoker(): Poker {
        val index = Random.nextInt(pokerList.size)
        val poker: Poker = pokerList.get(index)
        pokerList.removeAt(index)
        return poker
    }

    // get several cards by the number of players
    fun getSeveralCards(playerNumber:Int): List<Poker> {
        val pokers = arrayListOf<Poker>()
        for (i in 1..playerNumber) {
            pokers.add(getOnePoker())
        }
        return pokers
    }

}