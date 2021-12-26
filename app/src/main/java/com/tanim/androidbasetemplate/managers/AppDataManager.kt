package com.bcsprostuti.tanim.bcsprostuti.managers

import PreferenceManager
import Session
import com.tanim.androidbasetemplate.data.database.AppDatabase
import com.tanim.androidbasetemplate.utils.NetworkUtils
import javax.inject.Singleton
import javax.inject.Inject

import java.util.*

@Singleton
class AppDataManager @Inject constructor(
    private val session: Session,
    private val preferenceManager: PreferenceManager,
    override var database: AppDatabase,
    //override val currentTime: Flow<Long>
) : DataManager {

    @Inject
    lateinit var networkUtils: NetworkUtils

    @Inject
    lateinit var appDataHelper: AppDataHelper


    override fun getDeviceId(): String? {
        return preferenceManager.getDeviceId()
    }

    override fun setDeviceId(deviceId: String?) {
        preferenceManager.setDeviceId(deviceId)
    }

    override fun getEmail(): String? {
        return preferenceManager.getEmail()
    }


    @Synchronized
    override fun isLoggedIn(): Boolean {
        return session.isLoggedIn()
    }

    override fun isNetworkConnectionAvailable(): Boolean {
        return networkUtils.isNetworkConnected
    }

    @Synchronized
    override fun getApiToken(): String? {
        return session.getApiToken()
    }

    override fun setApiToken(apiToken: String?) {
        session.setApiToken(apiToken)
    }

    override fun getFcmToken(): String? {
        return preferenceManager.getFcmToken()
    }

    override fun setFcmToken(token: String?) {
        preferenceManager.setFcmToken(token)
    }

    @Synchronized
    override fun getUser(): User? {
        return session.getUser()
    }


    override fun setUser(user: User?) {
        session.setUser(user)
    }

    @Synchronized
    override fun logOut() {
        if (isLoggedIn()) {
            session.logOut()
        }
    }

    @Synchronized
    override fun logIn() {
        session.logIn()
    }

    override fun setAuthenticationListener(listener: Session.AuthenticationListener) {
        session.setAuthenticationListener(listener)
    }



}