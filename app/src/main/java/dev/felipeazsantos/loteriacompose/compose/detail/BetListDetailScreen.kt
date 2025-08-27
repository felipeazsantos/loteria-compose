package dev.felipeazsantos.loteriacompose.compose.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.felipeazsantos.loteriacompose.App
import dev.felipeazsantos.loteriacompose.R
import dev.felipeazsantos.loteriacompose.data.Bet
import dev.felipeazsantos.loteriacompose.ui.theme.LoteriaComposeTheme
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BetListDetailScreen(type: String) {
    val bets = remember { mutableStateListOf<Bet>() }
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR"))

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn{
            itemsIndexed(bets) { index, bet ->
                Text(
                    stringResource(
                        id = R.string.list_response,
                        index,
                        sdf.format(bet.date),
                        bet.numbers
                    ),
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp)
                )
            }

        }
    }

    val db = (LocalContext.current.applicationContext as App).db

    Thread {
        val res = db.betDao().getNumbersByType(type)
        bets.clear()
        bets.addAll(res)
    }.start()
}

@Preview(showBackground = true)
@Composable
fun BetListDetailScreenPreview() {
    LoteriaComposeTheme {
        BetListDetailScreen(type = "megasena")
    }
}