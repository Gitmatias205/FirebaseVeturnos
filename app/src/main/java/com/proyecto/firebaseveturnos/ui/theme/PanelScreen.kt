package com.proyecto.firebaseveturnos

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PanelScreen(navController: NavController, firebaseAuth: FirebaseAuth) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Panel de Control",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Text(
                text = "👤 Ver Usuarios",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        Toast.makeText(context, "Ver Usuarios", Toast.LENGTH_SHORT).show()
                        // navController.navigate("usuarios")
                    }
            )

            Text(
                text = "📅 Ver Citas",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        Toast.makeText(context, "Ver Citas", Toast.LENGTH_SHORT).show()
                    }
            )

            Text(
                text = "⚙️ Configuración",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        Toast.makeText(context, "Configuración", Toast.LENGTH_SHORT).show()
                    }
            )
        }

        Button(
            onClick = {
                firebaseAuth.signOut()
                Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                navController.navigate("login") {
                    popUpTo("panel") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            Text("Cerrar Sesión")
        }
    }
}
