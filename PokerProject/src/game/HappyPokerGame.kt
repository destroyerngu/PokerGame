package game

import player.PlayerManager
import poker.PokerManager
import rule.PokerRuleManager
import util.*
import kotlin.system.exitProcess

class HappyPokerGame: IGame(){
    private var lastBet: Int = 0 // record the money that the last player bet
    private var tableMoney: Int = 0 // record the money on the table
    private val playerManager = PlayerManager.sharedPlayerManager
    private val pokerManager = PokerManager.sharedPokerManager
    private var allInStartIndex = INVALID_NUM  //   use this for helping control loop in all-in rule
    private var aliveCount = 0  // record the online player in current game
            set(value) {
                field = value
                if (aliveCount == 1) {
                    // pay attention to this occasion
                    playerManager.awardWinner(tableMoney)
                    gameOver()
                }
            }
    private var currentPlayerIndex = 0
            set(value) {
                field = value
                if (isAllIn()) {
                    if (currentPlayerIndex == allInStartIndex) {
                        val player = PokerRuleManager.findMaxPlayer(playerManager.getPlayerList())
                        val minMoney = playerManager.findMinBetMoney()
                        val extraMoney = playerManager.returnExtraMoney(minMoney)
                        tableMoney -= extraMoney
                        playerManager.awardWinner(player, tableMoney)
                        gameOver()
                    }
                }
            }

    companion object {
        val sharedHappyPokerGame: HappyPokerGame by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            HappyPokerGame()
        }
    }
    fun getCurrentPlayerIndex(): Int = currentPlayerIndex

    init {
        aliveCount = playerManager.getPlayerList().size
    }
    override fun start() {
        chooseTable()
        dealCards()
        startGame()



    }



    // start current game officially
    private fun startGame() {
        while (true) {
            // show the list of gamePlayer when the game begin
            showPlayerInformation()
            // show current player who is using
            showCurrentPlayerInfo(playerManager.getPlayerWithIndex(currentPlayerIndex))
            println("当前桌面金币数为:$tableMoney$")
            if (isAllIn() || !playerManager.isMoneyEnough(currentPlayerIndex, lastBet)) {
                showAllinMenu()
            }
            else {
                showNormalMenu()
            }
            when(getChoice()) {
                1   ->  {
                    // bet or all-in
                    if (isAllIn() || !playerManager.isMoneyEnough(currentPlayerIndex, lastBet)) {
                        allIn()
                    }
                    else {
                        betMoney()
                    }
                }
                2   ->  {
                    // followBet or discard
                    if (isAllIn() || !playerManager.isMoneyEnough(currentPlayerIndex, lastBet)) {
                        giveUp(currentPlayerIndex)
                    }
                    else {
                        followBet()
                    }
                }
                3   ->  {
                    giveUp(currentPlayerIndex)
                }
                4   ->  {
                    compare()
                }
                5   ->  {
                    allIn()
                }
            }
            currentPlayerIndex = playerManager.findNextPlayer(currentPlayerIndex + 1)
        }
    }

    private fun compare() {
        // If you decide to compare the value of your poker with another' poker,
        // you need to bet before this comparison and this situation of bet is default
        followBet()
        while (true) {
            var playerId = choosePlayer(currentPlayerIndex + 1)
            var player = playerManager.getPlayerWithIndex(playerId - 1)
            if (player.isAlive == OFF_LINE) {
                "玩家已经出局,请重新选择!!!".showWithEnter()
            }
            else {
                var currentPlayer = playerManager.getPlayerWithIndex(currentPlayerIndex)
                var result = PokerRuleManager.compareTwpPokers(currentPlayer.poker, player.poker)
                if (result) {
                    "Winning!!!".showWithEnter()
                    giveUp(playerId - 1)
                    break
                }
                else {
                    "Sorry,you lose!!!".showWithEnter()
                    giveUp(currentPlayerIndex)
                    break
                }
            }
        }
    }

    fun gameOver() {
        "GameOver!!!".showWithEnter()
        showPlayerInformation()
        exitProcess(0)
    }

    private fun giveUp(number: Int) {
        playerManager.changePlayerStatusWithIndex(number, OFF_LINE)
        aliveCount--
    }

    private fun followBet() {
        var player = playerManager.getPlayerWithIndex(currentPlayerIndex)
        tableMoney += player.followBetMoney(lastBet)
    }

    // all in
    private fun allIn() {
        if (allInStartIndex == INVALID_NUM) {
            allInStartIndex = currentPlayerIndex
        }
        var player = playerManager.getPlayerWithIndex(currentPlayerIndex)
        tableMoney += player.clearAllMoney()
    }
    // bet
    private fun betMoney() {
        var player = playerManager.getPlayerWithIndex(currentPlayerIndex)
        lastBet = inputMoney(player.money, lastBet)
        // deduct money
        player.deductMoney(lastBet)
        tableMoney += lastBet
    }

    // justify player is in all-in status
    private fun isAllIn() = (allInStartIndex != INVALID_NUM)

    // choose the table that the user seat
    private fun chooseTable() {
        showTableMenu()
        var bottomBet = getBottomBet(getChoice())
        playerManager.playerBottomBet(bottomBet)
        tableMoney = bottomBet * playerManager.getPlayerList().size
    }
    private fun dealCards() {
        dealPokersToPlayers(
            pokerManager.getSeveralCards(playerManager.getPlayerList().size),
            playerManager.getPlayerList())
        showPlayerInformation()
        println("当前桌面金币数为:${tableMoney}$")
    }

}