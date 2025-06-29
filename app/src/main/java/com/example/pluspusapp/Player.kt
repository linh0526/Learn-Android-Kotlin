package com.example.pluspusapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var score: Int = 0
) 