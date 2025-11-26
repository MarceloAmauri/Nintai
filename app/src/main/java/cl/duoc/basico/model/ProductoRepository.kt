package cl.duoc.basico.model

import cl.duoc.basico.network.ApiClient

class ProductoRepository {

    private val api = ApiClient.productoApi

    suspend fun obtenerProductos(): List<ProductoDto> {
        return api.getProductos()
    }
}
