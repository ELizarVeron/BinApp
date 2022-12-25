package com.android.binapp.presentation

import androidx.lifecycle.*
import com.android.binapp.data.repository.Repository

import com.android.binapp.domain.models.BinInfoEntity

class MainViewModel(private val repository: Repository) : ViewModel() {
    private var mutableNoInfo = MutableLiveData<Boolean>()
    val noInfo: LiveData<Boolean>

    init {
        mutableNoInfo.value = false
        noInfo = mutableNoInfo
    }

    var allRecords: LiveData<List<BinInfoEntity>> =
        repository.loadRecordsFromDB().asLiveData()

    fun loadInfoFromAPI(bin: Int) {
        repository.getRecordFromApi(bin, mutableNoInfo)
    }

    fun clear() {
        repository.deleteAll()
    }

    fun delete(id: Int) {
        repository.delete(id)
    }


}