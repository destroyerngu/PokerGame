package game

abstract class AbstractGameCenter {

    fun start() {
        login()
        chooseGame()
    }
    abstract fun chooseGame()
    abstract fun login()
}