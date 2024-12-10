package com.example.truecolors.data.remote.retrofit

import com.example.truecolors.data.remote.response.DetectionResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("api/predicted")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): DetectionResponse
}