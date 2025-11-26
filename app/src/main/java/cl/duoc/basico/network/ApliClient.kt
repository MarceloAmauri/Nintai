package cl.duoc.basico.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import cl.duoc.basico.model.ProductoApi   // 👈 OJO: viene de .model

object ApiClient {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val productoApi: ProductoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductoApi::class.java)
    }
}
