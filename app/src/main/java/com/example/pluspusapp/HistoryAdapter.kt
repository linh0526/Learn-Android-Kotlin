package com.example.pluspusapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    
    private var historyList = emptyList<ScoreHistory>()
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    
    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textPlayerName: TextView = itemView.findViewById(R.id.textHistoryPlayerName)
        val textScoreChange: TextView = itemView.findViewById(R.id.textScoreChange)
        val textScoreTransition: TextView = itemView.findViewById(R.id.textScoreTransition)
        val textTimestamp: TextView = itemView.findViewById(R.id.textTimestamp)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(itemView)
    }
    
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentHistory = historyList[position]
        
        holder.textPlayerName.text = currentHistory.playerName
        
        // Format score change: [+5] or [-3]
        val changeText = if (currentHistory.changeAmount >= 0) {
            "[+${currentHistory.changeAmount}]"
        } else {
            "[${currentHistory.changeAmount}]"
        }
        holder.textScoreChange.text = changeText
        
        // Format score transition: [oldScore → newScore]
        val transitionText = "[${currentHistory.oldScore} → ${currentHistory.newScore}]"
        holder.textScoreTransition.text = transitionText
        
        // Format timestamp
        val timestamp = timeFormat.format(Date(currentHistory.timestamp))
        holder.textTimestamp.text = timestamp
    }
    
    override fun getItemCount() = historyList.size
    
    fun setHistory(history: List<ScoreHistory>) {
        this.historyList = history
        notifyDataSetChanged()
    }
} 