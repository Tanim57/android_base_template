
import com.bcsprostuti.tanim.bcsprostuti.data.local.LocalRepository
import com.bcsprostuti.tanim.bcsprostuti.data.mapper.ResourceString
import com.bcsprostuti.tanim.bcsprostuti.data.reporitory.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class DataRepositoryImpl (private val remoteRepository:RemoteRepository,
                          private val localRepository: LocalRepository,
                          private val ioDispatcher: CoroutineContext
): DataRepository {



    override suspend fun getPackages(): Flow<Resource<ResourceString, ResourceString>> {
        return flow {
            emit(localRepository.getPackages())
        }.flowOn(ioDispatcher)
    }

    override suspend fun getPackages(user: Int): Flow<Resource<ResourceString, ResourceString>> {
        return flow {
            emit(remoteRepository.getPackages(user))
        }.flowOn(ioDispatcher)
    }

    override suspend fun insert() {
        localRepository.insert()
    }
}
