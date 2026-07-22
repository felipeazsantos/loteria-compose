package dev.felipeazsantos.loteriacompose.compose.megasena

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.felipeazsantos.loteriacompose.App
import dev.felipeazsantos.loteriacompose.R
import dev.felipeazsantos.loteriacompose.data.Bet
import dev.felipeazsantos.loteriacompose.ui.component.LoItemType
import dev.felipeazsantos.loteriacompose.ui.component.LoNumberTextField
import dev.felipeazsantos.loteriacompose.ui.theme.LoteriaComposeTheme
import kotlinx.coroutines.launch
import kotlin.random.Random


@Composable
fun MegaScreen() {
    val resultsToSave = mutableListOf<String>()

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        val db = (LocalContext.current.applicationContext as App).db

        var qtdNumbers by remember { mutableStateOf("") }
        var qtdBets by remember { mutableStateOf("") }
        val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
        var result by remember { mutableStateOf("") }
        var showAlertDialog by remember { mutableStateOf(false) }

        val keyboardController = LocalSoftwareKeyboardController.current
        val scrollState = rememberScrollState()
        val scope = rememberCoroutineScope()

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            LoItemType(
                name = "Mega Sena"
            )

            Text(
                text = stringResource(id = R.string.announcement),
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(all = 20.dp)
            )

            LoNumberTextField(
                value = qtdNumbers,
                label = R.string.quantity_label,
                placeholder = R.string.quantity,
            ) {
                if (it.length < 3) {
                    qtdNumbers = validateInput(it)
                }
            }

            LoNumberTextField(
                value = qtdBets,
                label = R.string.bets_quantity_label,
                placeholder = R.string.bets_quantity,
                imeAction = ImeAction.Done
            ) {
                if (it.length < 3) {
                    qtdBets = validateInput(it)
                }
            }

            OutlinedButton(
                enabled = qtdNumbers.isNotEmpty() && qtdBets.isNotEmpty(),
                onClick = {
                    val bets = qtdBets.toInt()
                    val numbers = qtdNumbers.toInt()
                    if (bets < 1 || bets > 10) {
                        scope.launch {
                            snackBarHostState.showSnackbar("Máximo número de apostas permitida é 10")
                        }
                    } else if (numbers < 6 || numbers > 15) {
                        scope.launch {
                            snackBarHostState.showSnackbar("Números devem ser de 6 à 15")
                        }
                    } else {
                        result = ""
                        resultsToSave.clear()

                        for (i in 1..bets) {
                            val res = numberGenerator(numbers)
                            resultsToSave.add(res)
                            result += "[$i]"
                            result += res
                            result += "\n\n"
                        }
                        showAlertDialog = true
                    }

                    keyboardController?.hide()
                }
            ) {
                Text(stringResource(id = R.string.bets_generate))
            }

            Text(text = result)
        }

        Box {
            SnackbarHost(
                modifier = Modifier.align(Alignment.BottomCenter),
                hostState = snackBarHostState
            )
        }

        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = {
                    showAlertDialog = false
                },
                confirmButton = {
                    TextButton(onClick = { showAlertDialog = false }) {
                        Text(text = stringResource(android.R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        Thread {
                            for (res in resultsToSave) {
                                val bet = Bet(type = "megasena", numbers = res)
                                db.betDao().insert(bet)
                            }
                        }.start()

                        showAlertDialog = false
                    }) {
                        Text(text = stringResource(R.string.save))
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

private fun numberGenerator(qtd: Int): String {
    val numbers = mutableSetOf<Int>()

    while (true) {
        val n = Random.nextInt(60) + 1
        numbers.add(n)

        if (numbers.size == qtd) {
            break
        }
    }

    return numbers.joinToString(" - ")
}

private fun validateInput(input: String): String {
    return input.filter { it.isDigit() }
}

@Preview(showBackground = true)
@Composable
fun FormScreenPreview() {
    LoteriaComposeTheme {
        MegaScreen()
    }
}