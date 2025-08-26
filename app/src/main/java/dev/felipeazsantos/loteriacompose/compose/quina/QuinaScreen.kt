package dev.felipeazsantos.loteriacompose.compose.quina

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.felipeazsantos.loteriacompose.ui.theme.LoteriaComposeTheme

@Composable
fun QuinaScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Gray
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun QuinaScreenPreview() {
    LoteriaComposeTheme {
        QuinaScreen()
    }
}
