package com.khoahoang183.data.features.file.repository

import android.webkit.MimeTypeMap
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.khoahoang183.data.base.Resource
import com.khoahoang183.data.base.network.NetworkRepository
import com.khoahoang183.data.features.file.FileApiService
import com.khoahoang183.data.features.file.network.UploadFileRequest
import com.khoahoang183.data.features.file.network.UploadMultipleFileRequest
import com.khoahoang183.model.features.FileModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.Locale

interface FileRepository {
    fun uploadFile(params: UploadFileRequest): Flow<Resource<MutableList<FileModel>?>>

    fun uploadMultipleFile(params: UploadMultipleFileRequest): Flow<Resource<MutableList<FileModel>?>>
}

class FileRepositoryImpl constructor(
    private val apiService: FileApiService,
) : FileRepository, NetworkRepository() {

    override fun uploadFile(params: UploadFileRequest): Flow<Resource<MutableList<FileModel>?>> {
        return safeNetworkFlow(
            apiService.uploadFile(files = createUploadFileMultipart(params.path)),
        )
    }

    override fun uploadMultipleFile(params: UploadMultipleFileRequest): Flow<Resource<MutableList<FileModel>?>> {
        return safeNetworkFlow(
            apiService.uploadMultipleFile(files = createUploadMultipleFileMultipart(params.pathList)),
        )
    }

    private fun createUploadFileMultipart(filePath: String): MultipartBody.Part {
        val file = getFileRequest(filePath)
        val mimeType = getMimeTypeFromExtension(filePath, "image/jpeg")
        val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("files", file.name, requestFile)
    }

    private fun createUploadMultipleFileMultipart(filePaths: List<String>): List<MultipartBody.Part> {
        val requestFiles = mutableListOf<MultipartBody.Part>()
        filePaths.forEach { filePath ->
            val file = getFileRequest(filePath)
            val mimeType = getMimeTypeFromExtension(filePath, "image/jpeg")
            val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
            requestFiles.add(MultipartBody.Part.createFormData("files", file.name, requestFile))
        }
        return requestFiles
    }

    private fun getFileRequest(filePath: String): File {
        return if (filePath.startsWith("file")) {
            filePath.toUri().toFile()
        } else {
            File(filePath)
        }
    }

    private fun getMimeTypeFromExtension(path: String?, fallback: String = "*/*"): String {
        path ?: return fallback
        return MimeTypeMap.getFileExtensionFromUrl(path)
            ?.run { MimeTypeMap.getSingleton().getMimeTypeFromExtension(this.lowercase(Locale.US)) }
            ?: fallback
    }
}