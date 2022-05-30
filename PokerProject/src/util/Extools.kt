package util

import player.Player
import poker.Poker
    // deal with pokers
fun dealPokersToPlayers(
    pokers: List<Poker>,
    players: List<Player>
) {
    for ((i,item) in pokers.withIndex()) {
        players[i].poker = item
    }

}