package dev.felipeazsantos.loteriacompose.compose.quina

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.felipeazsantos.loteriacompose.compose.megasena.MegaScreen
import dev.felipeazsantos.loteriacompose.ui.theme.LoteriaComposeTheme


@Composable
fun QuinaScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) { }
}

@Preview(showBackground = true)
@Composable
fun FormScreenPreview() {
    LoteriaComposeTheme {
        QuinaScreen()
    }
}