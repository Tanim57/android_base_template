package com.bcsprostuti.tanim.bcsprostuti.data.local


import Resource
import com.bcsprostuti.tanim.bcsprostuti.data.mapper.ResourceString
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun getPackages(): Resource<ResourceString,ResourceString>
    suspend fun insert()
}