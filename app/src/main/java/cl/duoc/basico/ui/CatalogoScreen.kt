package cl.duoc.basico.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.basico.R
import cl.duoc.basico.viewmodel.CartViewModel

// ── Modelo + demo ──
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    @DrawableRes val imageRes: Int? = null
)

val demoProducts = listOf(
    Product("camisa_sakura", "Camisa Sakura", 54990.0, R.drawable.camisa_nintai),
    Product("polera",        "Destroy Tee",   39990.0, R.drawable.polera_nintai),
    Product("drapped",       "Draped Tee",    19990.0, R.drawable.drapped_nintai),
    Product("chaqueta",       "Chaqueta Coat",    30000.0, R.drawable.prenda1),
    Product("pantalones",       "Pantalon Coat",    35000.0, R.drawable.prenda2),
    Product("shirt",       "Yami Shirt",    81000.0, R.drawable.prenda3),
    Product("pantalon_hakama",       "Pantalon Hakama",    66000.0, R.drawable.prenda4),
    Product("pantalon_ballon",       "Pantalon Ballon",    75000.0, R.drawable.prenda5),
    Product("shirt_fencing",       "Shirt Fencing",    80000.0, R.drawable.prenda6),
    Product("pantalon_hakamav1",       "Pantalon Hakama V1",    67000.0, R.drawable.prenda7),
    Product("pantalon_hakamav2",       "Pantalon Hakama V2",    62990.0, R.drawable.prenda8),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    onVerCarrito: () -> Unit,
    onBack: () -> Unit,
    cartViewModel: CartViewModel = viewModel()
) {
    // Observamos el carrito
    val cartState by cartViewModel.state.collectAsState()

    // 🔍 estado del buscador
    var searchQuery by remember { mutableStateOf("") }

    // Lista filtrada según el texto
    val filteredProducts = remember(searchQuery, cartState) {
        if (searchQuery.isBlank()) {
            demoProducts
        } else {
            demoProducts.filter { prod ->
                prod.name.contains(searchQuery, ignoreCase = true) ||
                        prod.id.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Ropa Nintai", color = Color.White) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Volver", color = Color.White)
                    }
                },
                actions = {
                    TextButton(onClick = onVerCarrito) {
                        Text("Carrito", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF121212),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { p ->
        Column(
            modifier = Modifier
                .padding(p)
                .fillMaxSize()
                .padding(12.dp)
        ) {

            // 🔍 Caja de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar producto") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // 📋 Lista de productos (filtrada)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredProducts, key = { it.id }) { prod ->
                    val qty = cartState.items.firstOrNull { it.productId == prod.id }?.qty ?: 0

                    ProductCard(
                        product = prod,
                        qty = qty,
                        onPlus = {
                            cartViewModel.add(prod.id, prod.name, prod.price, prod.imageRes)
                        },
                        onMinus = {
                            val item = cartState.items.firstOrNull { it.productId == prod.id }
                            if (item != null) cartViewModel.decrement(item)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    qty: Int,
    onPlus: () -> Unit,
    onMinus: () -> Unit
) {
    Card(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (product.imageRes != null) {
                Image(
                    painter = painterResource(product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.size(72.dp)
                )
                Spacer(Modifier.width(12.dp))
            }
            Column(Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.SemiBold)
                Text("$${"%,.0f".format(product.price)}")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onMinus,
                    enabled = qty > 0,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black
                    )
                ) {
                    Text("–")
                }

                Text(qty.toString(), color = Color.Black)

                Button(
                    onClick = onPlus,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("+")
                }
            }
        }
    }
}

