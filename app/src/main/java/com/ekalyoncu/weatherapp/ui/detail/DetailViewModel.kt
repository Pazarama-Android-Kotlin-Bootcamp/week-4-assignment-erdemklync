package com.ekalyoncu.weatherapp.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val detailLiveData = savedStateHandle.getLiveData<String>("daily")

}