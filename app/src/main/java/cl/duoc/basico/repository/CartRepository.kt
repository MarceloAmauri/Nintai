package cl.duoc.basico.repository

import cl.duoc.basico.model.CartDao
import cl.duoc.basico.model.CartItemEntity
import kotlinx.coroutines.flow.Flow

class CartRepository(private val dao: CartDao) {

    fun observeCart(): Flow<List<CartItemEntity>> = dao.observeCart()

    suspend fun addOrIncrement(productId: String, name: String, price: Double, imageRes: Int?) {
        val existing = dao.findByProduct(productId)
        if (existing == null) {
            dao.insert(CartItemEntity(productId = productId, name = name, price = price, qty = 1, imageRes = imageRes))
        } else {
            dao.update(existing.copy(qty = existing.qty + 1))
        }
    }

    suspend fun decrementOrRemove(item: CartItemEntity) {
        if (item.qty > 1) dao.update(item.copy(qty = item.qty - 1))
        else dao.delete(item)
    }

    suspend fun clear() = dao.clear()
}
