package com.example.pluspusapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Player::class, ScoreHistory::class],
    version = 2,
    exportSchema = false
)
abstract class ScoreKeeperDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun scoreHistoryDao(): ScoreHistoryDao
    
    companion object {
        @Volatile
        private var INSTANCE: ScoreKeeperDatabase? = null
        
        fun getDatabase(context: Context): ScoreKeeperDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScoreKeeperDatabase::class.java,
                    "scorekeeper_database"
                ).fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 