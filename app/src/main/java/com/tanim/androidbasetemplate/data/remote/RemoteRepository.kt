
import com.bcsprostuti.tanim.bcsprostuti.data.mapper.ResourceString
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun getPackages(user: Int): Resource<ResourceString, ResourceString>
}