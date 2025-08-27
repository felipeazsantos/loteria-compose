package dev.felipeazsantos.loteriacompose.compose.megasena

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.felipeazsantos.loteriacompose.App
import dev.felipeazsantos.loteriacompose.R
import dev.felipeazsantos.loteriacompose.data.Bet
import dev.felipeazsantos.loteriacompose.ui.component.AutoTextDropDown
import dev.felipeazsantos.loteriacompose.ui.component.LoItemType
import dev.felipeazsantos.loteriacompose.ui.component.LoNumberTextField
import dev.felipeazsantos.loteriacompose.ui.theme.LoteriaComposeTheme
import kotlinx.coroutines.launch
import java.util.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MegaScreen(onBlackClick: () -> Unit, onMenuClick: (String) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Apostar") },
                    navigationIcon = {
                        IconButton(onClick = {
                            onBlackClick()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            onMenuClick("megasena")
                        }) {
                            Icon(
                                imageVector = Icons.Filled.List,
                                contentDescription = ""
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            MegaSenaContentScreen(modifier = Modifier.padding(top = paddingValues.calculateTopPadding()))
        }
    }
}

@Composable
fun MegaSenaContentScreen(modifier: Modifier) {
    val resultsToSave = remember { mutableStateListOf<String>() }

    val db = (LocalContext.current.applicationContext as App).db

    val errorBets = stringResource(id = R.string.error_bets)
    val errorNumbers = stringResource(id = R.string.error_numbers)

    var qtdNumber by remember { mutableStateOf("") }
    var qtdBets by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    var snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    var showAlertDialog by remember { mutableStateOf(false) }

    val rules = stringArrayResource(id = R.array.array_bet_rules)
    var selectedItem by remember { mutableStateOf(rules.first()) }

    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(scrollState)
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

        Column(
            modifier = Modifier.width(280.dp)
        ){
            AutoTextDropDown(
                label = stringResource(id = R.string.bet_rule),
                initial = selectedItem,
                list = rules.toList()
            ) {
                selectedItem = it
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
                    resultsToSave.clear()

                    for (i in 1..bets) {
                        val res = numberGenerator(numbers, rules.indexOf(selectedItem))
                        resultsToSave.add(res)

                        result += "[$i] "
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

        Text(
            text = result
        )
    }

    Box {
        SnackbarHost(
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
                    Thread {
                        for (res in resultsToSave) {
                            val bet = Bet(type = "megasena", numbers = res)
                            db.betDao().insert(bet)
                        }
                    }.start()

                    showAlertDialog = false
                }) {
                    Text(text = stringResource(id = R.string.save))
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

private fun numberGenerator(qtd: Int, rule: Int): String {
    val numbers = mutableSetOf<Int>()

    while (numbers.size < qtd) {
        val n = Random().nextInt(60) + 1

        if (rule == 1) {
            if (n % 2 != 0) continue
        } else if (rule == 2) {
            if (n % 2 == 0) continue
        }

        numbers.add(n)
    }

    return numbers.joinToString(" - ")
}

private fun validateInput(input: String): String {
    return input.filter { it in "0123456789" }
}

@Preview(showBackground = true)
@Composable
fun MegaScreenPreview() {
    LoteriaComposeTheme {
        MegaScreen(onBlackClick = {}) {

        }
    }
}