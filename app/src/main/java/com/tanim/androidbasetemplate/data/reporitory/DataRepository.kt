package com.bcsprostuti.tanim.bcsprostuti.data.reporitory

import Resource
import com.bcsprostuti.tanim.bcsprostuti.data.mapper.ResourceString
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun getPackages(): Flow<Resource<ResourceString,ResourceString>>
    suspend fun getPackages(user: Int): Flow<Resource<ResourceString, ResourceString>>
    suspend fun insert()
}