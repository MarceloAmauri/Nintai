package cl.duoc.basico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.duoc.basico.model.SessionManager
import cl.duoc.basico.ui.AppNavHost
import cl.duoc.basico.ui.theme.BasicoTheme

class MainActivity : ComponentActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // creamos la instancia de SessionManager con el contexto de la Activity
        sessionManager = SessionManager(this)

        setContent {
            BasicoTheme {
                // ahora le pasas sessionManager a la navegación
                AppNavHost(sessionManager)
            }
        }
    }
}
