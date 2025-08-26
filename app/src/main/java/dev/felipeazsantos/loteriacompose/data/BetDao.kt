package dev.felipeazsantos.loteriacompose.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BetDao {
    @Insert
    fun insert(bet: Bet)
}