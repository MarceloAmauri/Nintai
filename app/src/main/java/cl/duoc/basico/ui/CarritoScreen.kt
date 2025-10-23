package cl.duoc.basico.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.basico.model.CartItemEntity
import cl.duoc.basico.viewmodel.CartViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    onBack: () -> Unit,
    cartViewModel: CartViewModel = viewModel()
) {
    val state by cartViewModel.state.collectAsState()

    // 🔸 Estados y scope SOLO para el botón Comprar
    var isLoading by remember { mutableStateOf(false) }
    var showCongrats by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tu Carrito",
                        color = Color.White, // 🔹 Letras blancas
                        fontWeight = FontWeight.Bold // 🔹 Negrita
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Volver", color = Color.White)
                    }
                },
                actions = {
                    if (state.items.isNotEmpty()) {
                        TextButton(onClick = { cartViewModel.clear() }) {
                            Text("Vaciar", color = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black // 🔹 Fondo negro de la barra
                )
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (state.items.isEmpty()) {
                Text("El carrito está vacío.")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.items, key = { it.id }) { item ->
                        CartRow(
                            item = item,
                            onMinus = { cartViewModel.decrement(item) }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    "Total: $${"%,.0f".format(state.total)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                // 🔘 Botón "Comprar" con animación de espera y alerta al finalizar
                Button(
                    onClick = {
                        if (isLoading) return@Button
                        isLoading = true
                        scope.launch {
                            kotlinx.coroutines.delay(2000) // ⏱️ espera simulada
                            isLoading = false
                            showCongrats = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black, // 🔹 Fondo negro
                        contentColor = Color.White    // 🔹 Texto blanco
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.items.isNotEmpty() && !isLoading
                ) {
                    if (isLoading) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(Modifier.width(12.dp))
                            Text("Procesando…", fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Text("Comprar", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // Alerta tras completar la “compra”
    if (showCongrats) {
        AlertDialog(
            onDismissRequest = { showCongrats = false },
            title = { Text("Compra realizada") },
            text = { Text("Felicidades por comprar en NINTAI") },
            confirmButton = {
                TextButton(onClick = { showCongrats = false }) {
                    Text("OK")
                }
            }
        )
    }
}


@Composable
private fun CartRow(
    item: CartItemEntity,
    onMinus: () -> Unit
) {
    Card(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.SemiBold)
                Text("x${item.qty}  •  $${"%,.0f".format(item.price)} c/u")
            }
            TextButton(onClick = onMinus) { Text("1") }
        }
    }
}
