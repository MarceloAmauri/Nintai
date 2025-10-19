package cl.duoc.basico.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.basico.R
import cl.duoc.basico.viewmodel.CartViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Button
import androidx.compose.material3.Text


// ── Modelo + demo (mueve esto a otro archivo si ya lo tienes definido) ──
data class Product(val id: String, val name: String, val price: Double, @DrawableRes val imageRes: Int? = null)
val demoProducts = listOf(
    Product("camisa_sakura", "Camisa Sakura", 54990.0,R.drawable.camisa_nintai),
    Product("polera",    "Destroy Tee",      39990.0, R.drawable.polera_nintai),
    Product("drapped",     "Draped Tee",     19990.0, R.drawable.drapped_nintai),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    onVerCarrito: () -> Unit,
    onBack: () -> Unit,
    cartViewModel: CartViewModel = viewModel()
) {
    // Observamos el carrito para conocer la cantidad actual de cada producto
    val cartState by cartViewModel.state.collectAsState()

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
                    containerColor = Color(0xFF121212), // fondo oscuro
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { p ->
        LazyColumn(
            contentPadding = p,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(demoProducts, key = { it.id }) { prod ->
                val qty = cartState.items.firstOrNull { it.productId == prod.id }?.qty ?: 0

                ProductCard(
                    product = prod,
                    qty = qty,
                    onPlus = {
                        // +1 (crea o incrementa en Room)
                        cartViewModel.add(prod.id, prod.name, prod.price, prod.imageRes)
                    },
                    onMinus = {
                        // -1 (si llega a 0, lo borra)
                        val item = cartState.items.firstOrNull { it.productId == prod.id }
                        if (item != null) cartViewModel.decrement(item)
                    }
                )
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

            // Contador: –  +
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
