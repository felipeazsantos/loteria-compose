package dev.felipeazsantos.loteriacompose.compose.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.felipeazsantos.loteriacompose.ui.theme.LoteriaComposeTheme

@Composable
fun BetListDetailScreen(type: String) {
    Text("Tipo de dado: $type")
}

@Preview(showBackground = true)
@Composable
fun BetListDetailScreenPreview() {
    LoteriaComposeTheme {
        BetListDetailScreen(type = "megasena")
    }
}