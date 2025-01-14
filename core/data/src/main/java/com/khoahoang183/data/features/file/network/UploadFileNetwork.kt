package com.khoahoang183.data.features.file.network

import com.khoahoang183.data.base.BaseParams
import com.khoahoang183.data.base.network.BaseDataResponse
import com.khoahoang183.model.features.FileModel
import com.squareup.moshi.JsonClass

data class UploadFileRequest(
    val path: String
) : BaseParams()
@JsonClass(generateAdapter = true)
class UploadFileResponse : BaseDataResponse<MutableList<FileModel>>()
