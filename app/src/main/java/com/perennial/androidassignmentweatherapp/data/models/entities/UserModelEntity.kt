package com.perennial.androidassignmentweatherapp.data.models.entities

import androidx.room.*

@Entity(
    tableName = "tb_user",
    indices = [Index(value = ["user_email"], unique = true)]
)
data class UserModelEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int? = null,

    @ColumnInfo(name = "user_email")
    val userEmail: String? = null,

    @ColumnInfo(name = "user_name")
    val userName: String? = null,

    @ColumnInfo(name = "password_text")
    val userPassword: String? = null,

    @ColumnInfo(name = "is_logged_in")
    val isLoggedIn: Boolean = false
) {
    @Ignore
    val userConfirmPassword: String? = null
}
