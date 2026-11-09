package com.atom.myapp.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atom.myapp.R
import com.atom.myapp.data.MatchData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainOnBoarding(onFinish: () -> Unit) {
    val matches = listOf(
        MatchData(
            league = "LIGA MX",
            team1 = "Santos Laguna",
            team1Logo = R.drawable.santos_laguna,
            team2 = "León",
            team2Logo = R.drawable.leon
        ),
        MatchData(
            league = "LA LIGA",
            team1 = "Barcelona",
            team1Logo = R.drawable.barcelona,
            team2 = "Real Madrid",
            team2Logo = R.drawable.real_madrid
        ),
        MatchData(
            league = "PREMIER LEAGUE",
            team1 = "Manchester City",
            team1Logo = R.drawable.manchester_city,
            team2 = "Manchester United",
            team2Logo = R.drawable.manchester_united
        )
    )

    val pagerState = rememberPagerState(pageCount = { matches.size })

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            ScoreboardPage(match = matches[page])
        }

        if (pagerState.currentPage == matches.size - 1) {
            Button(
                onClick = onFinish,
                colors = ButtonDefaults.buttonColors(
                    containerColor = matches[pagerState.currentPage].headerBackgroundColor
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp)
            ) {
                Text(text = "FINALIZAR", color = matches[pagerState.currentPage].headerTextColor)
            }
        }
    }
}