package com.atom.myapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atom.myapp.R

@Composable
fun HomeScreen(onResetOnboarding: () -> Unit = {}) {
    val primaryTeal = Color(0xFF0F5B78)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TopBar "FutStats - Home"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(primaryTeal),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "FutStats - Home",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Imagen del cuervo FutStats
        Image(
            painter = painterResource(id = R.drawable.futstats),
            contentDescription = "Logo FutStats",
            modifier = Modifier
                .size(220.dp)
                .padding(16.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Mira las estadísticas de tus equipos favoritos en las diferentes ligas de todo el mundo.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Botón para volver a ver el OnBoarding
        Button(
            onClick = onResetOnboarding,
            colors = ButtonDefaults.buttonColors(containerColor = primaryTeal)
        ) {
            Text("Ver OnBoarding de nuevo", color = Color.White)
        }
    }
}