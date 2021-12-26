
interface PreferenceManager {

    fun getApiToken(): String?

    fun setApiToken(apiToken: String?)

    fun getFcmToken(): String?

    fun setFcmToken(token: String?)

    fun getDeviceId(): String?

    fun setDeviceId(deviceId: String?)

    fun getEmail(): String?

    fun getUser(): User?

    fun setUser(user: User?)


}