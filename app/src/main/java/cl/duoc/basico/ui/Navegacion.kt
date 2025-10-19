package cl.duoc.basico.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*

@Composable
fun AppNavHost() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = NavRoutes.BIENVENIDA) {

        composable(NavRoutes.BIENVENIDA) {
            Bienvenida(
                onRegistrarse = { nav.navigate(NavRoutes.REGISTRO) }
            )
        }

        composable(NavRoutes.REGISTRO) {
            RegistroScreen(
                onRegistradoOk = { nav.navigate(NavRoutes.LOGIN) },
                onBack = { nav.popBackStack() }
            )
        }

        composable(NavRoutes.LOGIN) {
            LoginScreen(
                onLoginOk = { nav.navigate(NavRoutes.CATALOGO) { popUpTo(NavRoutes.BIENVENIDA) { inclusive = false } } },
                onBack = { nav.popBackStack() }
            )
        }

        composable(NavRoutes.CATALOGO) {
            CatalogoScreen(
                onVerCarrito = { nav.navigate(NavRoutes.CARRITO) },
                onBack = { nav.popBackStack() }
            )
        }

        composable(NavRoutes.CARRITO) {
            CarritoScreen(
                onBack = { nav.popBackStack() }
            )
        }
    }
}
