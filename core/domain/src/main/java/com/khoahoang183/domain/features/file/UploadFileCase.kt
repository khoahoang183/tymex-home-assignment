package com.khoahoang183.domain.features.file

import com.khoahoang183.domain.base.BaseUseCase
import com.khoahoang183.data.base.Resource
import com.khoahoang183.data.features.file.network.UploadFileRequest
import com.khoahoang183.data.features.file.repository.FileRepository
import com.khoahoang183.model.features.FileModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadFileCase @Inject constructor(
    private val repository: FileRepository
) : BaseUseCase<Any, UploadFileRequest>() {

    override fun execute(params: UploadFileRequest): Flow<Resource<MutableList<FileModel>?>> {
        return repository.uploadFile(params = params)
    }
}