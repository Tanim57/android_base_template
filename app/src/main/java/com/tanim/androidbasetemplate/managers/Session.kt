
interface Session {
    fun isLoggedIn(): Boolean
    fun getUser():User?
    fun setUser(user: User?)
    fun getApiToken():String?
    fun setApiToken(token:String?)

    fun logOut()
    fun logIn()

    interface AuthenticationListener {
        fun onUserLoggedOut()
        fun onUserLogIn()
    }

    fun setAuthenticationListener(listener: AuthenticationListener)
}