package util

const val DEFAULT_NAME = "jack"
const val DEFAULT_PASSWORD = "123"

val GAME_MENU_LIST = arrayOf("欢乐大比拼","欢乐斗地主","四川麻将")

// tableList
val TABLE_LIST = arrayOf("100","200","500")

// getBottomBet in number
// ***经典写法***
fun getBottomBet(choice: Int) = TABLE_LIST[choice - 1].toInt()

// pokerNumberList
val POKER_NUMBER_LIST = arrayOf("2","3","4","5","6","7","8","9","10","J","Q","K","A")
// pokerSuitList
val POKER_SUIT_LIST = arrayOf("♦","♣","♥","♠")
// playerName
val PLAYER_NAME_LIST = arrayOf("Alan","Rose","Jack","Tom")
// all-in choose list
val ALL_IN_BET_MENU = arrayOf("All in","弃牌")
// normal choose list
val NORMAL_BET_MENU = arrayOf("下注", "跟住", "弃牌", "比牌", "all in")
//  invalid num
const val INVALID_NUM = -100
// the status of player
const val ON_LINE = true
const val OFF_LINE = false