package com.shukhaev.rentateamtz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shukhaev.rentateamtz.data.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)

    @Query("SELECT * FROM users_table")
    fun getAllUsers(): Flow<List<User>>
}