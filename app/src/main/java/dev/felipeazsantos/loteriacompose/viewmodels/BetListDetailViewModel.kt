package dev.felipeazsantos.loteriacompose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.felipeazsantos.loteriacompose.data.Bet
import dev.felipeazsantos.loteriacompose.data.BetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BetListDetailViewModel(
    private val type: String,
    private val betRepository: BetRepository
) : ViewModel() {
    private val _bets = MutableStateFlow<List<Bet>>(emptyList())
    val bets = _bets.asStateFlow()

    init {
        viewModelScope.launch {
            _bets.value = betRepository.getBets(type)
        }
    }
}