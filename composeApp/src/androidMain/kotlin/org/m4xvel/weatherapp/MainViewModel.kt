package org.m4xvel.weatherapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    private val _showButton = mutableStateOf(false)
    val showButton: State<Boolean> = _showButton

    fun setSearchText(text: String) {
        _searchText.value = text
        _showButton.value = text.isNotEmpty()
    }

    fun clearSearchText() {
        _searchText.value = ""
        _showButton.value = false
    }
}