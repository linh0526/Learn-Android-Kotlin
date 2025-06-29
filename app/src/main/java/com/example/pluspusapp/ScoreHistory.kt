package com.example.pluspusapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score_history")
data class ScoreHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playerId: Int,
    val playerName: String,
    val oldScore: Int,
    val newScore: Int,
    val changeAmount: Int,
    val timestamp: Long = System.currentTimeMillis()
) 