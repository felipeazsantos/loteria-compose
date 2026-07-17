package dev.felipeazsantos.loteriacompose.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.felipeazsantos.loteriacompose.compose.home.HomeScreen
import dev.felipeazsantos.loteriacompose.compose.megasena.MegaScreen
import dev.felipeazsantos.loteriacompose.compose.quina.QuinaScreen

enum class AppRouter(val route: String) {
    HOME("home"),
    MEGA_SENA("megasena"),
    QUINA("quina")
}

@Composable
fun LoteriaApp() {
    val navController = rememberNavController()
    LoteriaAppNavHost(navController)
}

@Composable
fun LoteriaAppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRouter.HOME.route
    ) {
        composable(AppRouter.HOME.route) {
            HomeScreen() {
                val router = when (it.id) {
                    1 -> AppRouter.MEGA_SENA
                    2 -> AppRouter.QUINA
                    else -> AppRouter.HOME
                }

                navController.navigate(router.route)
            }
        }
        composable(AppRouter.MEGA_SENA.route) { MegaScreen() }
        composable(AppRouter.MEGA_SENA.route) { QuinaScreen() }
    }
}

