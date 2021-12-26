package com.tanim.androidbasetemplate.di.module

import androidx.core.util.Supplier
import com.tanim.androidbasetemplate.base.BaseActivity
import com.bcsprostuti.tanim.bcsprostuti.managers.DataManager
import com.tanim.androidbasetemplate.base.ViewModelFactory
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity<*, *>) {
    @Provides
    fun provideMainViewModel(dataManager: DataManager?): MainViewModel {
        val supplier: Supplier<MainViewModel> =
            Supplier { MainViewModel(apiInterface, dataManager) }
        val factory: ViewModelFactory<MainViewModel> =
            ViewModelFactory<T>(MainViewModel::class.java, supplier)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }
}