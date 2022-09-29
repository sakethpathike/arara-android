package com.sakethh.arara.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Calendar

class HomeScreenViewModel : ViewModel() {
    val currentTime = mutableStateOf("")
     val listForHeader = mutableListOf<String>("Warrior-arts","News")
    init {
        viewModelScope.launch {
            when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> {
                    currentTime.value = "Good Morning"
                }
                in 12..15 -> {
                    currentTime.value = "Good Afternoon"
                }
                in 16..22 -> {
                    currentTime.value = "Good Evening"
                }
                in 23 downTo 0 -> {
                    currentTime.value = "Good Night?"
                }
                else -> {
                    currentTime.value = "Everything Fine?"
                }
            }
        }
    }
}