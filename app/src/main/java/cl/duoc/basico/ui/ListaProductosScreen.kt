package cl.duoc.basico.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.basico.model.ProductoDto
import cl.duoc.basico.model.ProductoRepository
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    private val repo = ProductoRepository()

    // 👇 estas son las 3 propiedades que usas en la UI
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

                // Llamada real a la API (jsonplaceholder /posts)
                val resultado = repo.obtenerProductos()
                productos = resultado

            } catch (e: Exception) {
                errorMessage = "Error al cargar productos: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
