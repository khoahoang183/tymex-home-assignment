package com.khoahoang183.data.base.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khoahoang183.data.common.dao.UserDao
import com.khoahoang183.model.features.GithubUserModel

@Database(
    entities = [GithubUserModel::class],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
//@TypeConverters(value = [])
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}