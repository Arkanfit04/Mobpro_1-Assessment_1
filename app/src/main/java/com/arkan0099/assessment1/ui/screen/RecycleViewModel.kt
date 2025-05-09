import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkan0099.assessment1.database.CatatanDao
import com.arkan0099.assessment1.model.Catatan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RecycleViewModel(private val catatanDao: CatatanDao) : ViewModel() {

    fun getRecycleBinNotes(): Flow<List<Catatan>> = catatanDao.getDeletedCatatan()

    fun restoreNote(id: Long) = viewModelScope.launch {
        catatanDao.restoreById(id)
    }

    fun deleteForever(id: Long) = viewModelScope.launch {
        catatanDao.deletePermanently(id)
    }
}
