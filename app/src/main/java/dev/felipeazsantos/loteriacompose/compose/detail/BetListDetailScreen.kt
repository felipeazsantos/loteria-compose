package dev.felipeazsantos.loteriacompose.compose.detail

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dev.felipeazsantos.loteriacompose.App
import dev.felipeazsantos.loteriacompose.ui.theme.LoteriaComposeTheme

@Composable
fun BetListDetailScreen(type: String) {
    val db = (LocalContext.current.applicationContext as App).db

    Thread {
        val bets = db.betDao().getNumbersByType(type)
        Log.i("TESTE", bets.toList().toString())
    }.start()

    Text("Tipo de dado: $type")
}

@Preview(showBackground = true)
@Composable
fun BetListDetailScreenPreview() {
    LoteriaComposeTheme {
        BetListDetailScreen(type = "megasena")
    }
}