
import com.bcsprostuti.tanim.bcsprostuti.data.mapper.ResourceString
import com.bcsprostuti.tanim.bcsprostuti.data.mapper.TextResourceString
import com.bcsprostuti.tanim.bcsprostuti.managers.DataManager
import com.tanim.androidbasetemplate.data.Constant
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val dataManager: DataManager) : RemoteRepository{
    override suspend fun getPackages(user: Int): Resource<ResourceString, ResourceString> {
        TODO("Not yet implemented")
    }


    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!dataManager.isNetworkConnectionAvailable()) {
            return TextResourceString(Constant.ERROR_NO_CONNECTION)
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                TextResourceString(Utility.getCommonErrorFromCode(responseCode))
            }
        } catch (e: IOException) {
            return TextResourceString(Constant.ERROR_FAILED_CONNECTION)
        }
    }
}