package game

import player.*
import util.*

class GameCenter: AbstractGameCenter() {
    lateinit var user: User
    lateinit var game: IGame
    var tableMoney: Int = 0
    override fun chooseGame() {
        showGameMenu()
        getChoice() //The function is developing,so it is just a kind of decoration
        game = HappyPokerGame()
        game.start()
    }

    override fun login() {
        while (true) {
            "请输入用户名:".show()
            var username = getData()
//            "".showWithEnter()
            "请输入密码:".show()
            var pwd = getData()
            if (Server.checkUser(username, pwd)) {
                user = User(username, pwd)
                "登录成功!!!".showWithEnter()
                break
            }
            else {
                "用户名或密码不正确".showWithEnter()
            }
        }
    }

}