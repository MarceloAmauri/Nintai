package cl.duoc.basico.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import cl.duoc.basico.model.SessionManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    sessionManager: SessionManager
) {
    LaunchedEffect(Unit) {
        delay(1000) // opcional, solo para mostrar el splash un momento

        if (sessionManager.isLoggedIn()) {
            navController.navigate(NavRoutes.CATALOGO) {   // 👈 sin ".route"
                popUpTo(NavRoutes.SPLASH) { inclusive = true }
            }
        } else {
            navController.navigate(NavRoutes.LOGIN) {
                popUpTo(NavRoutes.SPLASH) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Mi App")
    }
}



