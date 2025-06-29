package com.example.pluspusapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class PlayerAdapter(
    private val context: Context,
    private val onScoreChange: (Player, Int) -> Unit,
    private val onNameChange: (Player, String) -> Unit,
    private val onPlayerDelete: (Player) -> Unit
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {
    
    private var players = emptyList<Player>()
    
    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardPlayer)
        val textPlayerName: TextView = itemView.findViewById(R.id.textPlayerName)
        val textScore: TextView = itemView.findViewById(R.id.textScore)
        val buttonMinus: ImageButton = itemView.findViewById(R.id.buttonMinus)
        val buttonPlus: ImageButton = itemView.findViewById(R.id.buttonPlus)
        val buttonEdit: ImageButton = itemView.findViewById(R.id.buttonEdit)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(itemView)
    }
    
    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val currentPlayer = players[position]
        
        // Alternating card colors (orange/gray)
        val cardColor = if (position % 2 == 0) {
            ContextCompat.getColor(context, R.color.card_orange)
        } else {
            ContextCompat.getColor(context, R.color.card_gray)
        }
        holder.cardView.setCardBackgroundColor(cardColor)
        
        holder.textPlayerName.text = currentPlayer.name
        holder.textScore.text = currentPlayer.score.toString()
        
        // Score increment
        holder.buttonPlus.setOnClickListener {
            onScoreChange(currentPlayer, currentPlayer.score + 1)
        }
        
        // Score decrement
        holder.buttonMinus.setOnClickListener {
            onScoreChange(currentPlayer, currentPlayer.score - 1)
        }
        
        // Edit player name
        holder.buttonEdit.setOnClickListener {
            showEditNameDialog(currentPlayer)
        }
        
        // Long press to delete
        holder.itemView.setOnLongClickListener {
            showDeleteConfirmDialog(currentPlayer)
            true
        }
    }
    
    private fun showEditNameDialog(player: Player) {
        val editText = EditText(context)
        editText.setText(player.name)
        editText.selectAll()
        
        AlertDialog.Builder(context)
            .setTitle("Edit Player Name")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val newName = editText.text.toString().trim()
                if (newName.isNotEmpty()) {
                    onNameChange(player, newName)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showDeleteConfirmDialog(player: Player) {
        AlertDialog.Builder(context)
            .setTitle("Delete Player")
            .setMessage("Are you sure you want to delete ${player.name}?")
            .setPositiveButton("Delete") { _, _ ->
                onPlayerDelete(player)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    override fun getItemCount() = players.size
    
    fun setPlayers(players: List<Player>) {
        this.players = players
        notifyDataSetChanged()
    }
} 