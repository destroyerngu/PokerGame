package util

class Server {
    companion object {
        fun checkUser(name: String, pwd: String): Boolean {
            return name == DEFAULT_NAME && pwd == DEFAULT_PASSWORD
        }
    }
}