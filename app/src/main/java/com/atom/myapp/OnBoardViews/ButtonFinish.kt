package com.atom.myapp.onBoardViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ButtonFinish(currentPage: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(bottom = 100.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        if (currentPage == 2) {
            OutlinedButton(onClick = onClick) {
                Text(
                    text = "Finalizar",
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 40.dp),
                    color = Color.Gray
                )
            }
        }
    }
}