package cl.duoc.basico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.duoc.basico.ui.AppNavHost
import cl.duoc.basico.ui.theme.BasicoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicoTheme {
                // Aquí se inicia toda la navegación (Bienvenida → Registro → Login → Catálogo → Carrito)
                AppNavHost()
            }
        }
    }
}

