package cl.duoc.basico.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.basico.R

@Composable
fun Bienvenida(onRegistrarse: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Bienvenid@ a Nintai",
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Imagen (logo)
        Image(
            painter = painterResource(id = R.drawable.nintai_png),
            contentDescription = "Logo de Nintai",
            modifier = Modifier
                .size(250.dp)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Botón para ir al registro
        Button(
            onClick = onRegistrarse,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .width(220.dp)
                .height(55.dp)
        ) {
            Text(
                text = "Ir al Registro",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
