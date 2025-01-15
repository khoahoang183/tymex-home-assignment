package com.khoahoang183.data.common.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khoahoang183.model.features.GithubUserModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGithubUsers(users: List<GithubUserModel>)

    @Query("SELECT * FROM GithubUserModel")
    suspend fun selectAllGithubUsers(): List<GithubUserModel>
}