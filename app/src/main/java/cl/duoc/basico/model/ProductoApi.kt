package cl.duoc.basico.model

import retrofit2.http.GET

interface ProductoApi {

    @GET("posts")
    suspend fun getProductos(): List<ProductoDto>
}
