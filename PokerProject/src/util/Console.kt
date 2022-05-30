package util

import player.Player
import player.PlayerManager

//输入数据
fun getData(): String = readLine()!!

var currentMenuList: Array<String> = arrayOf()

// TO deal with the player’money and the bet that input in the patten of bet
fun inputMoney(max: Int, min: Int): Int {
    while (true) {
        "请输入下注金额:".show()
        try {
            var input = getData().toInt()
            if (input > max || input < min) {
                "下注金额必须满足$min-${max}之间".showWithEnter()
            }
            else {
                "下注成功!".showWithEnter()
                return input
            }

        }catch (e : Exception){
            "您的输入不合法!!!".showWithEnter()
        }
    }


}

// show menu ***
fun showMenu(menus: Array<String>) {
    for ((index,item) in menus.withIndex()) {
        var tag = if (menus.contentEquals(TABLE_LIST))"$" else ""
        "${index + 1}.$tag${item}\t".show()
    }
}
// show all-in choose menu
fun showAllinMenu() {
    currentMenuList = ALL_IN_BET_MENU
    showStarLine()
    showMenu(currentMenuList)
    "".showWithEnter()
    showStarLine()
}
// show normal choose menu
fun showNormalMenu() {
    currentMenuList = NORMAL_BET_MENU
    showStarLine()
    showMenu(currentMenuList)
    "".showWithEnter()
    showStarLine()
}
fun showGameMenu() {
    currentMenuList = GAME_MENU_LIST
    showStarLine()
    showMenu(currentMenuList)
    "".showWithEnter()
    showStarLine()
}

// 显示tableMenu
fun showTableMenu() {
    currentMenuList = TABLE_LIST
    showStarLine()
    showMenu(currentMenuList)
    "".showWithEnter()
}

// show all players information
fun showPlayerInformation() {
    showStarLine()
    for (player in PlayerManager.sharedPlayerManager.getPlayerList()) {
//        if (player.isAlive){
//            println(player)
//        }
        println(player)
    }
    showStarLine()

}

// show one player information who is playing this game
    fun showCurrentPlayerInfo(player: Player) {
        println("当前玩家:$player")
    }



fun getChoice(): Int {
    while (true) {
        "请输入:".show()
        try {
            val choice = getData().toInt()
            if (choice in 1..currentMenuList.size) {
                return choice
            }
        }catch (e: Exception) {}
        "输入不合法! ".showWithEnter()
    }

}
// choose a player to compare your poker with its poker,
// return the choice you made
fun choosePlayer(currentPlayerIndex: Int): Int {
    while (true) {
        "请选择比牌目标玩家:".show()
        try {
            var choice = getData().toInt()
            if (choice == currentPlayerIndex) {
                "您不能和自己比牌,请重新选择!!!".showWithEnter()
                continue
            }


            if (choice <0 || choice > PlayerManager.sharedPlayerManager.getPlayerList().size) {
                "比牌目标玩家序号不存在!".showWithEnter()
                continue
            }
            else {
                return choice
            }
        }catch (e: Exception) {
            "输入不合法!!!"
        }
    }


}

fun String.show() = print(this)

fun String.showWithEnter() = println(this)

//显示星星分割线
fun showStarLine() = println("********************************************")