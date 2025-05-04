package com.arkan0099.assessment1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkan0099.assessment1.database.CatatanDao
import com.arkan0099.assessment1.model.Catatan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: CatatanDao) : ViewModel() {

    val data: StateFlow<List<Catatan>> = dao.getCatatan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
}