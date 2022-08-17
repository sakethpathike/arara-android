package com.sakethh.arara.unreleased

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UnreleasedViewModel(private val unreleasedRepo: UnreleasedRepo=UnreleasedRepo()) : ViewModel() {
     fun getSongsData():List<UnreleasedResponse>{
            return unreleasedRepo.getSongsData().unreleasedResponse
       }
}