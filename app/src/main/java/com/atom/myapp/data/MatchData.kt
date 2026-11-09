package com.atom.myapp.data

import androidx.compose.ui.graphics.Color

data class MatchData(
    val league: String,
    val team1: String,
    val team1Logo: Int,
    val team2: String,
    val team2Logo: Int
) {
    val headerBackgroundColor: Color
        get() = when (league.uppercase()) {
            "LIGA MX" -> Color(0xFF1E7E34)       // Verde
            "PREMIER LEAGUE" -> Color(0xFF38003C) // Morado Azulado
            "LA LIGA" -> Color(0xFFFF6600)        // Naranja
            else -> Color(0xFF0F5B78)
        }

    val headerTextColor: Color
        get() = when (league.uppercase()) {
            "LA LIGA" -> Color.Black
            else -> Color.White
        }
}
