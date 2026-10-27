package com.yxhelzvl.ahuil.game.memorama.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MemoramaScreen(navController: NavController, memoramaViewModel: MemoramaViewModel) {
    val state = memoramaViewModel.state

    LaunchedEffect(Unit) {
        memoramaViewModel.resetGame()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Memorama",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Grid(cards = state.cards, onCardSelected = { memoramaViewModel.onCardSelected(it) })
        Spacer(modifier = Modifier.height(16.dp))
        if (state.cards.all { it.isMatched }) {
            Button(
                onClick = { memoramaViewModel.nextLevel() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Siguiente Nivel")
            }
        } else {
            Button(
                onClick = { memoramaViewModel.resetGame() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Reiniciar")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Parejas Encontradas: ${state.pairsFound}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun Grid(cards: List<Card>, onCardSelected: (Card) -> Unit) {
    Column {
        for (row in cards.chunked(4)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (card in row) {
                    CardView(card = card, onClick = { onCardSelected(card) })
                }
            }
        }
    }
}

@Composable
fun CardView(card: Card, onClick: () -> Unit) {
    val backgroundColor = if (card.isFaceUp || card.isMatched) {
        Brush.linearGradient(colors = listOf(Color.White, Color.LightGray))
    } else {
        Brush.linearGradient(colors = listOf(Color.Gray, Color.DarkGray))
    }

    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp)
            .shadow(8.dp, RoundedCornerShape(8.dp))
            .background(brush = backgroundColor, shape = RoundedCornerShape(8.dp))
            .clickable(enabled = !card.isFaceUp && !card.isMatched) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (card.isFaceUp || card.isMatched) {
            Text(
                text = card.content,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}