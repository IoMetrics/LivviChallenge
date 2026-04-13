package com.cristiano.livvichallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppBatteryIndicator(
    batteryLevel: Int,
    modifier: Modifier = Modifier
) {
    val safeLevel = batteryLevel.coerceIn(0, 100)

    val fillColor = if (safeLevel < 15) {
        Color.Red
    } else {
        Color(0xFF1565C0)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(14.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width((36 * safeLevel / 100).dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(fillColor)
                )
            }

            Spacer(modifier = Modifier.width(2.dp))

            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$safeLevel%",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}