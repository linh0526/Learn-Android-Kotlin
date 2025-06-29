package com.example.pluspusapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ScoreHistoryDao {
    @Query("SELECT * FROM score_history ORDER BY timestamp DESC")
    fun getAllHistory(): LiveData<List<ScoreHistory>>
    
    @Query("SELECT * FROM score_history WHERE playerId = :playerId ORDER BY timestamp DESC")
    fun getHistoryByPlayer(playerId: Int): LiveData<List<ScoreHistory>>
    
    @Insert
    suspend fun insertHistory(scoreHistory: ScoreHistory)
    
    @Query("DELETE FROM score_history")
    suspend fun deleteAllHistory()
    
    @Query("DELETE FROM score_history WHERE playerId = :playerId")
    suspend fun deleteHistoryByPlayer(playerId: Int)
    
    @Query("SELECT COUNT(*) FROM score_history")
    suspend fun getHistoryCount(): Int
} 