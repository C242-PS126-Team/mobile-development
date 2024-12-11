package com.example.truecolors.ui.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truecolors.data.remote.response.DetectionResponse
import com.example.truecolors.data.remote.repository.DetectionRepository
import kotlinx.coroutines.launch
import java.io.File

class HomeViewModel(private val repository: DetectionRepository) : ViewModel() {


    private val _predictions = MutableLiveData<DetectionResponse>()
    val predictions: LiveData<DetectionResponse> get() = _predictions

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: LiveData<Uri?> get() = _currentImageUri

    fun setImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }

    fun analyzeImage(imageFile: File) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.uploadImage(imageFile)
                _predictions.value = response
                _loading.value = false
            } catch (e: Exception) {
                _loading.value = false
                _error.value = e.message ?: "Unknown error"
            }
        }
    }
}
