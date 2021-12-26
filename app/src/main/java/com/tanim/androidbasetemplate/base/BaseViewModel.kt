/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.tanim.androidbasetemplate.base

import com.bcsprostuti.tanim.bcsprostuti.managers.DataManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bcsprostuti.tanim.bcsprostuti.data.mapper.ResourceString
import java.lang.ref.WeakReference
import kotlin.jvm.Synchronized

open class BaseViewModel<T : BaseContract.View>(@get:Synchronized val dataManager: DataManager) :
    ViewModel(), BaseContract.ViewModel<T> {

    var isLoading = MutableLiveData(false)
    var toastMessage: MutableLiveData<ResourceString> = SingleLiveEvent()
    var errorMessage: MutableLiveData<ResourceString> = SingleLiveEvent()

    var view: WeakReference<T>? = null

    override fun onCleared() {
        super.onCleared()
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }

    override fun bindView(view: T) {
        //if (this.view == null)
        this.view = WeakReference(view)
    }

    override fun unBind() {
        view = null
    }

    override fun getView(): T? {
        return if (view != null) view!!.get() else null
    }

    override fun logOut() {
        dataManager.logOut()
    }
}