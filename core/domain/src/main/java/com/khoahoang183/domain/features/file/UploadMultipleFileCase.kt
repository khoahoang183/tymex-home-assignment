package com.khoahoang183.domain.features.file

import com.khoahoang183.domain.base.BaseUseCase
import com.khoahoang183.data.base.Resource
import com.khoahoang183.data.features.file.network.UploadMultipleFileRequest
import com.khoahoang183.data.features.file.repository.FileRepository
import com.khoahoang183.model.features.FileModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadMultipleFileCase  @Inject constructor(
    private val repository: FileRepository
) : BaseUseCase<MutableList<FileModel>, UploadMultipleFileRequest>() {

    override fun execute(params: UploadMultipleFileRequest): Flow<Resource<MutableList<FileModel>?>> {
        return repository.uploadMultipleFile(params = params)
    }
}