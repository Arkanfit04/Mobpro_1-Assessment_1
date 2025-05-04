package com.arkan0099.assessment1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkan0099.assessment1.database.CatatanDao
import com.arkan0099.assessment1.model.Catatan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: CatatanDao) : ViewModel() {

    fun insert(task: String, detail: String, deadline: String, status: String) {
        val catatan = Catatan(
            task = task,
            detail = detail,
            deadline = deadline,
            status = status
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(catatan)
        }
    }
    suspend fun getCatatan(id : Long): Catatan? {
        return dao.getCatatanById(id)
    }

    fun update(id: Long, task: String, detail: String, deadline: String, status: String) {
        val catatan = Catatan(
            id = id,
            task = task,
            detail = detail,
            deadline = deadline,
            status = status
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(catatan)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}