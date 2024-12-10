package com.example.truecolors.data.remote.retrofit

import com.example.truecolors.data.remote.response.ScanResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("predicted")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): ScanResponse

}