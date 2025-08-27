package dev.felipeazsantos.loteriacompose.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.felipeazsantos.loteriacompose.compose.detail.BetListDetailScreen
import dev.felipeazsantos.loteriacompose.compose.home.HomeScreen
import dev.felipeazsantos.loteriacompose.compose.megasena.MegaScreen
import dev.felipeazsantos.loteriacompose.compose.quina.QuinaScreen

@Composable
fun LoteriaApp() {
    Scaffold { paddingValues ->
        val navController = rememberNavController()
        LoteriaAppNavHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

enum class AppRouter(val route: String) {
    HOME("home"),
    MEGA_SENA("megasena"),
    QUINA("quina"),
    BET_LIST_DETAIL("betListDetail")
}

@Composable
fun LoteriaAppNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = AppRouter.HOME.route,
        modifier = modifier
    ) {
        composable(AppRouter.HOME.route) {
            HomeScreen { item ->
                val router = when (item.id) {
                    1 -> AppRouter.MEGA_SENA
                    2 -> AppRouter.QUINA
                    else -> AppRouter.HOME
                }
                navController.navigate(router.route)
            }
        }

        composable(AppRouter.MEGA_SENA.route) {
            MegaScreen { type ->
                navController.navigate("${AppRouter.BET_LIST_DETAIL.route}/${type}")
            }
        }

        composable(AppRouter.QUINA.route) {
            QuinaScreen()
        }

        composable(
            route = AppRouter.BET_LIST_DETAIL.route + "/{type}",
            arguments = listOf(
                navArgument(name = "type") {
                    type = NavType.StringType
                }
            )
        ) {
            val type = it.arguments?.getString("type") ?: throw Exception("Tipo não encontrado")
            BetListDetailScreen(type = type)
        }
    }
}
