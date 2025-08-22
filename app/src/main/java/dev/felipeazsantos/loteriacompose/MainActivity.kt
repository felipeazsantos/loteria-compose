package dev.felipeazsantos.loteriacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.felipeazsantos.loteriacompose.ui.component.LoItemType
import dev.felipeazsantos.loteriacompose.ui.component.LoNumberTextField
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
                    startDestination = AppRouter.HOME.route
                ) {
                    composable(AppRouter.HOME.route) {
                        HomeScreen {
                            navController.navigate(AppRouter.LOTTERY_FORM.route)
                        }
                    }

                    composable(AppRouter.LOTTERY_FORM.route) {
                        FormScreen()
                    }
                }
            }
        }
    }
}

enum class AppRouter(val route: String) {
    HOME("home"),
    LOTTERY_FORM("lottery_form")
}

@Composable
fun HomeScreen(onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
        ) {
            LotteryItem("Mega Sena", onClick = onClick)
        }

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
       LoItemType(
           name = "Mega Sena",
           color = Color.White,
           bgColor = Green
       )
    }
}

@Composable
fun FormScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var qtdNumber by remember { mutableStateOf("") }
        var qtdBets by remember { mutableStateOf("") }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoItemType("Mega Sena")

            Text(
                text = stringResource(id = R.string.announcement),
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(20.dp)
            )

            LoNumberTextField(
                value = qtdNumber,
                label = R.string.mega_rule,
                placeholder = R.string.quantity,
                imeAction = ImeAction.Next,
            ) {
                if (it.length < 3) {
                    qtdNumber = validateInput(it)
                }
            }

            LoNumberTextField(
                value = qtdBets,
                label = R.string.bets,
                placeholder = R.string.bets_quantity,
                imeAction = ImeAction.Done,
            ) {
                if (it.length < 3) {
                    qtdBets = validateInput(it)
                }
            }

            OutlinedButton(onClick = {}) {
                Text(stringResource(id = R.string.bets_generate))
            }
        }
    }
}


private fun validateInput(input: String): String {
    return input.filter { it in "0123456789" }
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