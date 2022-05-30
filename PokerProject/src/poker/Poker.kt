package poker

class Poker(var pokerNumber: PokerNumber, var pokerSuit: PokerSuit) {
    override fun toString(): String {
        return "${this.pokerNumber.number}${this.pokerSuit.suit}"
    }
}