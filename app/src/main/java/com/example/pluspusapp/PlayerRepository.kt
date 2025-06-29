package com.example.pluspusapp

import androidx.lifecycle.LiveData

class PlayerRepository(
    private val playerDao: PlayerDao,
    private val scoreHistoryDao: ScoreHistoryDao
) {
    
    fun getAllPlayers(): LiveData<List<Player>> = playerDao.getAllPlayers()
    
    suspend fun insertPlayer(player: Player): Long = playerDao.insertPlayer(player)
    
    suspend fun updatePlayer(player: Player) = playerDao.updatePlayer(player)
    
    suspend fun deletePlayer(player: Player) = playerDao.deletePlayer(player)
    
    suspend fun deleteAllPlayers() = playerDao.deleteAllPlayers()
    
    suspend fun updatePlayerScore(playerId: Int, score: Int) = playerDao.updatePlayerScore(playerId, score)
    
    suspend fun updatePlayerName(playerId: Int, name: String) = playerDao.updatePlayerName(playerId, name)
    
    // Score History methods
    fun getAllHistory(): LiveData<List<ScoreHistory>> = scoreHistoryDao.getAllHistory()
    
    suspend fun insertHistory(scoreHistory: ScoreHistory) = scoreHistoryDao.insertHistory(scoreHistory)
    
    suspend fun deleteAllHistory() = scoreHistoryDao.deleteAllHistory()
    
    suspend fun deleteHistoryByPlayer(playerId: Int) = scoreHistoryDao.deleteHistoryByPlayer(playerId)
} 