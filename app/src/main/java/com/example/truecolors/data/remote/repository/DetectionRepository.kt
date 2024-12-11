// HomeRepository.kt
package com.example.truecolors.data.remote.repository

import com.example.truecolors.data.remote.response.DetectionResponse
import com.example.truecolors.data.remote.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class DetectionRepository(private val apiService: ApiService) {

    suspend fun uploadImage(imageFile: File): DetectionResponse {
        val requestImageFile: RequestBody = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("file", imageFile.name, requestImageFile)

        return try {
            val response = apiService.uploadImage(multipartBody)
            response
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }
}
