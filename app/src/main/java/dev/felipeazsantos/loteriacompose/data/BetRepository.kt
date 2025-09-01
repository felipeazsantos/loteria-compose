package dev.felipeazsantos.loteriacompose.data

class BetRepository(private val betDao: BetDao) {

    fun getBets(type: String) : List<Bet> {
        return betDao.getNumbersByType(type)
    }

    fun insertBet(bet: Bet) {
        betDao.insert(bet)
    }

    companion object {
        private var instance: BetRepository? = null

        fun getInstance(betDao: BetDao) {
            instance ?: BetRepository(betDao).also {
                instance = it
            }
        }
    }
}