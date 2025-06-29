package com.example.pluspusapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ScoreKeeperViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PlayerRepository
    val allPlayers: LiveData<List<Player>>
    val allHistory: LiveData<List<ScoreHistory>>
    
    init {
        val database = ScoreKeeperDatabase.getDatabase(application)
        val playerDao = database.playerDao()
        val scoreHistoryDao = database.scoreHistoryDao()
        repository = PlayerRepository(playerDao, scoreHistoryDao)
        allPlayers = repository.getAllPlayers()
        allHistory = repository.getAllHistory()
    }
    
    fun addPlayer(name: String) = viewModelScope.launch {
        val player = Player(name = name)
        repository.insertPlayer(player)
    }
    
    fun updatePlayerScore(playerId: Int, newScore: Int, playerName: String, oldScore: Int) = viewModelScope.launch {
        repository.updatePlayerScore(playerId, newScore)
        
        // Record the score change in history
        val changeAmount = newScore - oldScore
        val history = ScoreHistory(
            playerId = playerId,
            playerName = playerName,
            oldScore = oldScore,
            newScore = newScore,
            changeAmount = changeAmount
        )
        repository.insertHistory(history)
    }
    
    fun updatePlayerName(playerId: Int, newName: String) = viewModelScope.launch {
        repository.updatePlayerName(playerId, newName)
    }
    
    fun deletePlayer(player: Player) = viewModelScope.launch {
        repository.deletePlayer(player)
    }
    
    fun deleteAllPlayers() = viewModelScope.launch {
        repository.deleteAllPlayers()
    }
    
    fun incrementScore(playerId: Int, currentScore: Int, playerName: String) = viewModelScope.launch {
        val newScore = currentScore + 1
        repository.updatePlayerScore(playerId, newScore)
        
        // Record the score change in history
        val history = ScoreHistory(
            playerId = playerId,
            playerName = playerName,
            oldScore = currentScore,
            newScore = newScore,
            changeAmount = 1
        )
        repository.insertHistory(history)
    }
    
    fun decrementScore(playerId: Int, currentScore: Int, playerName: String) = viewModelScope.launch {
        val newScore = currentScore - 1
        repository.updatePlayerScore(playerId, newScore)
        
        // Record the score change in history
        val history = ScoreHistory(
            playerId = playerId,
            playerName = playerName,
            oldScore = currentScore,
            newScore = newScore,
            changeAmount = -1
        )
        repository.insertHistory(history)
    }
    
    fun clearAllHistory() = viewModelScope.launch {
        repository.deleteAllHistory()
    }
} 