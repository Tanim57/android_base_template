package com.bcsprostuti.tanim.bcsprostuti.managers

import PreferenceManager
import Session
import androidx.databinding.ObservableField
import com.tanim.androidbasetemplate.data.database.AppDatabase


interface DataManager : PreferenceManager, Session, DataHelper {

    var database: AppDatabase
    fun isNetworkConnectionAvailable(): Boolean

}