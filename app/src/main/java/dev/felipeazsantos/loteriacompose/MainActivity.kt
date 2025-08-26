package dev.felipeazsantos.loteriacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.felipeazsantos.loteriacompose.compose.LoteriaApp
import dev.felipeazsantos.loteriacompose.data.AppDatabase
import dev.felipeazsantos.loteriacompose.data.Bet
import dev.felipeazsantos.loteriacompose.ui.theme.LoteriaComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoteriaComposeTheme {
                LoteriaApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoteriaAppPreview() {
    LoteriaComposeTheme {
        LoteriaApp()
    }
}



