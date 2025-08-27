package dev.felipeazsantos.loteriacompose.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BetDao {
    @Query("SELECT * FROM bets WHERE type = :betType")
    fun getNumbersByType(betType: String) : List<Bet>

    @Insert
    fun insert(bet: Bet)
}