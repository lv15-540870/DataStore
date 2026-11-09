package com.atom.myapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atom.myapp.data.MatchData

@Composable
fun ScoreboardPage(match: MatchData) {
    var score1 by remember { mutableStateOf(0) }
    var score2 by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header de la liga
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(match.headerBackgroundColor),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = match.league,
                color = match.headerTextColor,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Controles de partido
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ----- EQUIPO 1 -----
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .border(2.dp, match.headerBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = match.team1Logo),
                        contentDescription = match.team1,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = match.team1, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(20.dp))

                IconButton(
                    onClick = { score1++ },
                    modifier = Modifier
                        .size(60.dp)
                        .background(match.headerBackgroundColor, CircleShape)
                ) {
                    Icon(Icons.Default.ArrowUpward, contentDescription = "Sumar", tint = match.headerTextColor)
                }

                Spacer(modifier = Modifier.height(16.dp))

                IconButton(
                    onClick = { if (score1 > 0) score1-- },
                    modifier = Modifier
                        .size(60.dp)
                        .background(match.headerBackgroundColor, CircleShape)
                ) {
                    Icon(Icons.Default.ArrowDownward, contentDescription = "Restar", tint = match.headerTextColor)
                }
            }

            // Marcador Central
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "$score1", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(text = "   VS   ", fontSize = 16.sp, color = Color.Gray)
                Text(text = "$score2", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            // ----- EQUIPO 2 -----
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .border(2.dp, match.headerBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = match.team2Logo),
                        contentDescription = match.team2,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = match.team2, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(20.dp))

                IconButton(
                    onClick = { score2++ },
                    modifier = Modifier
                        .size(60.dp)
                        .background(match.headerBackgroundColor, CircleShape)
                ) {
                    Icon(Icons.Default.ArrowUpward, contentDescription = "Sumar", tint = match.headerTextColor)
                }

                Spacer(modifier = Modifier.height(16.dp))

                IconButton(
                    onClick = { if (score2 > 0) score2-- },
                    modifier = Modifier
                        .size(60.dp)
                        .background(match.headerBackgroundColor, CircleShape)
                ) {
                    Icon(Icons.Default.ArrowDownward, contentDescription = "Restar", tint = match.headerTextColor)
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Reiniciar marcador
        IconButton(onClick = { score1 = 0; score2 = 0 }) {
            Icon(
                Icons.Default.Refresh,
                contentDescription = "Reiniciar",
                tint = match.headerBackgroundColor,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}