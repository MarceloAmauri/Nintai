package cl.duoc.basico.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.basico.model.ProductoDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    private val repo = ProductoRepository()

    var productos by mutableStateOf<List<ProductoDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun cargarProductos() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val resultado = repo.obtenerProductos()  // <-- ahora viene de jsonplaceholder
                productos = resultado

            } catch (e: Exception) {
                errorMessage = "Error al cargar productos: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
