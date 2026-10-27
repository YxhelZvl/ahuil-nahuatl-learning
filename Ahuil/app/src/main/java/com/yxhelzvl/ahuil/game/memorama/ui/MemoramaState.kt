package com.yxhelzvl.ahuil.game.memorama.ui

data class Card(
    val id: Int,
    val content: String,
    val isFaceUp: Boolean = false,
    val isMatched: Boolean = false
)

data class MemoramaState(
    val cards: List<Card> = emptyList(),
    val selectedCardId: Int? = null,
    val pairsFound: Int = 0,
    val isProcessing: Boolean = false
)