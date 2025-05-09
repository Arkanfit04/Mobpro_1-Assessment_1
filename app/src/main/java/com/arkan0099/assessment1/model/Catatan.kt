package com.arkan0099.assessment1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catatan")
data class Catatan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val task: String,
    val detail: String,
    val deadline: String,
    val status: String,
    val isDeleted: Boolean = false
)
