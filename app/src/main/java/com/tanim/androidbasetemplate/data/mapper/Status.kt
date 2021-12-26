
import androidx.annotation.Keep
import java.io.Serializable

@Keep
enum class Status : Serializable {
    SUCCESS, ERROR, LOADING
}