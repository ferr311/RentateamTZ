package com.shukhaev.rentateamtz.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shukhaev.rentateamtz.data.User

@Database(entities = [User::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}