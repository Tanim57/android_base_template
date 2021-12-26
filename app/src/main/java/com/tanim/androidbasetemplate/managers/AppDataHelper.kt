import com.bcsprostuti.tanim.bcsprostuti.managers.DataHelper
import com.tanim.androidbasetemplate.data.database.AppDatabase
import javax.inject.Inject

class AppDataHelper @Inject constructor(
    private val session: Session,
    private val preferenceManager: PreferenceManager,
    private val bcsProstutiDataBase: AppDatabase,
    //override val currentTime: Flow<Long>
) : DataHelper