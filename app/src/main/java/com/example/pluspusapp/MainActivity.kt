package com.example.pluspusapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlayerAdapter
    private lateinit var fabAddPlayer: FloatingActionButton
    
    private val viewModel: ScoreKeeperViewModel by viewModels()
    private var playerCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setupToolbar()
        setupViews()
        setupRecyclerView()
        observeViewModel()
        
        // Add default players if none exist
        viewModel.allPlayers.observe(this) { players ->
            if (players.isEmpty() && playerCount == 0) {
                addDefaultPlayers()
            }
        }
    }
    
    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }
    
    private fun setupViews() {
        recyclerView = findViewById(R.id.recyclerViewPlayers)
        fabAddPlayer = findViewById(R.id.fabAddPlayer)
        
        fabAddPlayer.setOnClickListener {
            showAddPlayerDialog()
        }
    }
    
    private fun setupRecyclerView() {
        adapter = PlayerAdapter(
            context = this,
            onScoreChange = { player, newScore ->
                viewModel.updatePlayerScore(player.id, newScore, player.name, player.score)
            },
            onNameChange = { player, newName ->
                viewModel.updatePlayerName(player.id, newName)
                Toast.makeText(this, "Player name updated", Toast.LENGTH_SHORT).show()
            },
            onPlayerDelete = { player ->
                viewModel.deletePlayer(player)
                Toast.makeText(this, getString(R.string.player_deleted), Toast.LENGTH_SHORT).show()
            }
        )
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    
    private fun observeViewModel() {
        viewModel.allPlayers.observe(this) { players ->
            adapter.setPlayers(players)
            updateEmptyState(players.isEmpty())
        }
    }
    
    private fun updateEmptyState(isEmpty: Boolean) {
        val emptyStateText = findViewById<TextView>(R.id.textEmptyState)
        if (isEmpty) {
            emptyStateText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyStateText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
    
    private fun showAddPlayerDialog() {
        viewModel.allPlayers.value?.let { players ->
            if (players.size >= 8) {
                Toast.makeText(this, getString(R.string.max_players_reached), Toast.LENGTH_SHORT).show()
                return
            }
        }
        
        val editText = EditText(this)
        editText.hint = getString(R.string.player_name_hint)
        
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.add_player))
            .setView(editText)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val playerName = editText.text.toString().trim()
                if (playerName.isNotEmpty()) {
                    viewModel.addPlayer(playerName)
                    playerCount++
                    Toast.makeText(this, getString(R.string.player_added), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.enter_player_name), Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
    
    private fun addDefaultPlayers() {
        viewModel.addPlayer("Player 1")
        viewModel.addPlayer("Player 2")
        playerCount = 2
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset_scores -> {
                showResetScoresDialog()
                true
            }
            R.id.action_clear_all -> {
                showClearAllDialog()
                true
            }
            R.id.action_history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun showResetScoresDialog() {
        AlertDialog.Builder(this)
            .setTitle("Reset Scores")
            .setMessage("Are you sure you want to reset all scores to 0?")
            .setPositiveButton("Reset") { _, _ ->
                resetAllScores()
                Toast.makeText(this, getString(R.string.scores_reset), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
    
    private fun showClearAllDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.clear_all))
            .setMessage("Are you sure you want to delete all players?")
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteAllPlayers()
                playerCount = 0
                Toast.makeText(this, getString(R.string.all_players_deleted), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
    
    private fun resetAllScores() {
        viewModel.allPlayers.value?.forEach { player ->
            viewModel.updatePlayerScore(
                playerId = player.id, 
                newScore = 0,
                playerName = player.name,
                oldScore = player.score
            )
        }
    }
}