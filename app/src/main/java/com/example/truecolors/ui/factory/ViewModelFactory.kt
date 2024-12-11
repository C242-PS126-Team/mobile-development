package com.example.truecolors.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.truecolors.data.remote.repository.DetectionRepository
import com.example.truecolors.ui.home.HomeViewModel

class ViewModelFactory(private val detectionRepository: DetectionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(detectionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}