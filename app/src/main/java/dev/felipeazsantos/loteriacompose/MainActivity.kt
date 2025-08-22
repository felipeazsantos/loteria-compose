package dev.felipeazsantos.loteriacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.felipeazsantos.loteriacompose.ui.theme.Green
import dev.felipeazsantos.loteriacompose.ui.theme.LoteriaComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoteriaComposeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen {
                            navController.navigate("lottery_form")
                        }
                    }

                    composable("lottery_form") {
                        FormScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LotteryItem("Mega Sena", onClick = onClick)
    }

}

@Composable
fun LotteryItem(name: String, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .wrapContentSize()
            .clickable{
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(Green)
        ) {
            Image(
                painter = painterResource(id = R.drawable.trevo),
                contentDescription = "",
                modifier = Modifier.size(100.dp).padding(10.dp)
            )
            Text(
                text = name,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun FormScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Green
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LoteriaComposeTheme {
        HomeScreen {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormScreenPreview() {
    LoteriaComposeTheme {
        FormScreen()
    }
}