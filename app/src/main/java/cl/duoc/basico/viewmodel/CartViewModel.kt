package cl.duoc.basico.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.basico.model.AppDatabase
import cl.duoc.basico.model.CartItemEntity
import cl.duoc.basico.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CartUiState(
    val items: List<CartItemEntity> = emptyList(),
    val total: Double = 0.0
)

class CartViewModel(app: Application) : AndroidViewModel(application = app) {

    private val repo = CartRepository(dao = AppDatabase.get(app).cartDao())

    val state = repo.observeCart()
        .map { list ->
            CartUiState(
                items = list,
                total = list.sumOf { it.price * it.qty }
            )
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, CartUiState())

    fun add(productId: String, name: String, price: Double, imageRes: Int?) {
        viewModelScope.launch {
            repo.addOrIncrement(productId, name, price, imageRes)
        }
    }

    fun decrement(item: CartItemEntity) {
        viewModelScope.launch {
            repo.decrementOrRemove(item)
        }
    }

    fun clear() {
        viewModelScope.launch {
            repo.clear()
        }
    }

    // 👇 NUEVO: eliminar COMPLETAMENTE un producto del carrito
    fun remove(item: CartItemEntity) {
        viewModelScope.launch {
            repo.removeItem(item)   // llamamos al repositorio
        }
    }
}



