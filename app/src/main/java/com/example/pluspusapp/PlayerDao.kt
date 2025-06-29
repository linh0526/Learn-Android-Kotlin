package com.example.pluspusapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players ORDER BY id ASC")
    fun getAllPlayers(): LiveData<List<Player>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: Player): Long
    
    @Update
    suspend fun updatePlayer(player: Player)
    
    @Delete
    suspend fun deletePlayer(player: Player)
    
    @Query("DELETE FROM players")
    suspend fun deleteAllPlayers()
    
    @Query("UPDATE players SET score = :score WHERE id = :playerId")
    suspend fun updatePlayerScore(playerId: Int, score: Int)
    
    @Query("UPDATE players SET name = :name WHERE id = :playerId")
    suspend fun updatePlayerName(playerId: Int, name: String)
} 