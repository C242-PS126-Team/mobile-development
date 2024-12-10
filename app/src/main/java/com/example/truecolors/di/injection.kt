package com.example.truecolors.di

import android.content.Context
import com.example.truecolors.data.remote.repository.ScanRepository
import com.example.truecolors.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): ScanRepository {
        val apiService = ApiConfig.getApiService()
        return ScanRepository.getInstance(apiService)
    }
}