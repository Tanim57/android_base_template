package com.tanim.androidbasetemplate

import com.bcsprostuti.tanim.bcsprostuti.managers.DataManager
import com.tanim.androidbasetemplate.base.BaseViewModel

class MainViewModel(dataManager: DataManager) :
    BaseViewModel<MainContract.View>(dataManager),MainContract.ViewModel {

}