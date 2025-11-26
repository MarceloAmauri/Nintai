package cl.duoc.basico.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.basico.model.SessionManager

@Composable
fun AppNavHost(sessionManager: SessionManager) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = NavRoutes.SPLASH   // Arranca en el Splash
    ) {

        // 🌊 Splash: decide a dónde ir según si está loggeado o no
        composable(NavRoutes.SPLASH) {
            SplashScreen(
                navController = nav,
                sessionManager = sessionManager
            )
        }

        // 👋 Pantalla de bienvenida (cuando NO está loggeado)
        composable(NavRoutes.BIENVENIDA) {
            Bienvenida(
                onRegistrarse = { nav.navigate(NavRoutes.REGISTRO) }
            )
        }

        // 📝 Registro
        composable(NavRoutes.REGISTRO) {
            RegistroScreen(
                onRegistradoOk = {
                    nav.navigate(NavRoutes.LOGIN) {
                        // Opcional: limpias un poco el back stack
                        popUpTo(NavRoutes.BIENVENIDA) { inclusive = false }
                    }
                },
                onBack = { nav.popBackStack() }
            )
        }

        // 🔐 Login
        composable(NavRoutes.LOGIN) {
            LoginScreen(
                onLoginOk = {
                    // Marcamos que el usuario quedó loggeado
                    sessionManager.setLoggedIn(true)

                    nav.navigate(NavRoutes.CATALOGO) {
                        // Limpiamos Bienvenida/Login para que al volver atrás no aparezcan
                        popUpTo(NavRoutes.BIENVENIDA) { inclusive = true }
                    }
                },
                onBack = {
                    // Intentamos volver normalmente
                    val pudoVolver = nav.popBackStack()
                    if (!pudoVolver) {
                        // Si no hay nada atrás, mandamos a Bienvenida
                        nav.navigate(NavRoutes.BIENVENIDA)
                    }
                }
            )
        }

        // 🛍️ Catálogo
        composable(NavRoutes.CATALOGO) {
            CatalogoScreen(
                onVerCarrito = { nav.navigate(NavRoutes.CARRITO) },
                onBack = { nav.popBackStack() }
            )
        }

        // 🧺 Carrito
        composable(NavRoutes.CARRITO) {
            CarritoScreen(
                onBack = { nav.popBackStack() }
            )
        }
    }
}


