package com.sakethh.arara.bookmarks

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakethh.arara.RealmDBObject
import com.sakethh.arara.home.selectedChipStuff.SelectedChipScreenViewModel
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BookMarkScreenViewModel(private val selectedChipScreenViewModel: SelectedChipScreenViewModel = SelectedChipScreenViewModel()) :
    ViewModel() {
    val savedData = mutableStateListOf<RealmDBObject>()
    init {
        savedData()
    }
    private fun savedData() {
        val data =
            selectedChipScreenViewModel.selectedChipScreenRealmDB.realm.query<RealmDBObject>("bookMarked == true")
                .asFlow()
        viewModelScope.launch {
            val dbDataProcess = async {
                data.collect {
                    it.list.forEach { realmDBObject ->
                        savedData.add(realmDBObject)
                    }
                }
            }
            dbDataProcess.await().also {
                dbDataProcess.cancel()
            }
        }
    }
}