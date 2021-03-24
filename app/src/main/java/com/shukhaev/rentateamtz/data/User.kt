package com.shukhaev.rentateamtz.data


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users_table")
@Parcelize
data class User(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String
) : Parcelable