package test

import game.GameCenter

fun main() {
    GameCenter().apply {
        this.login()
        this.chooseGame()
    }
}