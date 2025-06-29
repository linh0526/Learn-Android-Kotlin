package com.example.pluspusapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import android.view.View
import androidx.appcompat.widget.Toolbar

class HistoryActivity : AppCompatActivity() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryAdapter
    private val viewModel: ScoreKeeperViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        
        setupToolbar()
        setupRecyclerView()
        observeViewModel()
    }
    
    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbarHistory)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Lịch sử"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }
    
    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewHistory)
        adapter = HistoryAdapter()
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    
    private fun observeViewModel() {
        viewModel.allHistory.observe(this) { history ->
            adapter.setHistory(history)
            updateEmptyState(history.isEmpty())
        }
    }
    
    private fun updateEmptyState(isEmpty: Boolean) {
        val emptyStateText = findViewById<TextView>(R.id.textEmptyHistory)
        if (isEmpty) {
            emptyStateText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyStateText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_clear_history -> {
                showClearHistoryDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun showClearHistoryDialog() {
        AlertDialog.Builder(this)
            .setTitle("Xóa lịch sử")
            .setMessage("Bạn có chắc chắn muốn xóa toàn bộ lịch sử?")
            .setPositiveButton("Xóa") { _, _ ->
                clearHistory()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
    
    private fun clearHistory() {
        viewModel.clearAllHistory()
        Toast.makeText(this, "Đã xóa lịch sử", Toast.LENGTH_SHORT).show()
    }
} 