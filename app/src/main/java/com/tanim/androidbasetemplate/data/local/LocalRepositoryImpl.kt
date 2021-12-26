package com.bcsprostuti.tanim.bcsprostuti.data.local

import Resource
import android.content.Context
import com.bcsprostuti.tanim.bcsprostuti.data.mapper.ResourceString
import com.bcsprostuti.tanim.bcsprostuti.managers.DataManager
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl constructor(
    private val context: Context,
    private val dataManager: DataManager
) : LocalRepository {
    override suspend fun getPackages(): Flow<Resource<ResourceString, ResourceString>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert() {
        TODO("Not yet implemented")
    }
}