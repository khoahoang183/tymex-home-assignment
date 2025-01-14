package com.khoahoang183.data.features.file

import com.khoahoang183.data.features.file.network.UploadFileResponse
import com.khoahoang183.data.features.file.network.UploadMultipleFileResponse
import com.khoahoang183.model.features.FileModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileApiService {

    @Multipart
    @POST("files")
    fun uploadFile(
        @Part files: MultipartBody.Part,
    ): Call<MutableList<FileModel>>

    @Multipart
    @POST("files")
    fun uploadMultipleFile(
        @Part files: List<MultipartBody.Part>,
    ): Call<MutableList<FileModel>>
}