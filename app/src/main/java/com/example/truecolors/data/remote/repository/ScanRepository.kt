package com.example.truecolors.data.remote.repository

import com.example.truecolors.data.remote.retrofit.ApiService

class ScanRepository private constructor(
    private val apiService: ApiService,
){
    companion object {
        @Volatile
        private var instance: ScanRepository? = null

        fun getInstance(
            apiService: ApiService,
        ): ScanRepository =
            instance ?: synchronized(this) {
                instance ?: ScanRepository(apiService)
                    .also { instance = it }
            }
    }

    suspend fun getScanResult(){

    }



}