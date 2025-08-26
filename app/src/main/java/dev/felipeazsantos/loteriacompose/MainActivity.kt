package dev.felipeazsantos.loteriacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.felipeazsantos.loteriacompose.ui.component.LoItemType
import dev.felipeazsantos.loteriacompose.ui.component.LoNumberTextField
import dev.felipeazsantos.loteriacompose.ui.theme.Green
import dev.felipeazsantos.loteriacompose.ui.theme.LoteriaComposeTheme
import kotlinx.coroutines.launch
import java.util.Random

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
            .clickable {
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
        val errorBets = stringResource(id = R.string.error_bets)
        val errorNumbers = stringResource(id = R.string.error_numbers)

        var qtdNumber by remember { mutableStateOf("") }
        var qtdBets by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }
        var snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
        var showAlertDialog by remember { mutableStateOf(false) }

        val scope = rememberCoroutineScope()
        val keyboardController = LocalSoftwareKeyboardController.current
        val scrollState = rememberScrollState()

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(scrollState)
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

            OutlinedButton(
                enabled = qtdNumber.isNotEmpty() && qtdBets.isNotEmpty(),
                onClick = {
                    val bets = qtdBets.toInt()
                    val numbers = qtdNumber.toInt()

                    if (bets < 1 || bets > 10) {
                        scope.launch {
                            snackBarHostState.showSnackbar(errorBets)
                        }
                    } else if (numbers < 6 || numbers > 15) {
                        scope.launch {
                            snackBarHostState.showSnackbar(errorNumbers)
                        }
                    } else {
                        result = ""
                        for (i in 1..bets) {
                            result += "[$i] "
                            result += numberGenerator(numbers)
                            result += "\n\n"
                        }

                        showAlertDialog = true
                    }

                    keyboardController?.hide()
                }
            ) {
                Text(stringResource(id = R.string.bets_generate))
            }

            Text(
                text = result
            )
        }

        Box {
            SnackbarHost (
                modifier = Modifier.align(Alignment.BottomCenter),
                hostState = snackBarHostState,
            )
        }

        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {
                    TextButton(onClick = {
                        showAlertDialog = false
                    }) {
                        Text(text = stringResource(id = android.R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showAlertDialog = false
                    }) {
                        Text(text = stringResource(id = android.R.string.cancel))
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                text = {
                    Text(text = stringResource(id = R.string.good_luck))
                }
            )
        }
    }
}

private fun numberGenerator(qtd: Int) : String {
    val numbers = mutableSetOf<Int>()

    while (numbers.size < qtd) {
        val numberGenerated = Random().nextInt(60)
        numbers.add(numberGenerated + 1)
    }

    return numbers.joinToString(" - ")
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