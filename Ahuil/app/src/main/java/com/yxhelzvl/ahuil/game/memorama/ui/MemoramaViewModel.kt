package com.yxhelzvl.ahuil.game.memorama.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxhelzvl.ahuil.core.data.PalabrasObject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MemoramaViewModel : ViewModel() {

    var state by mutableStateOf(MemoramaState())
        private set
    private val palabrasObject = PalabrasObject
    private val contents = palabrasObject.palabras
    private var currentLevel = 2

    init {
        resetGame()
    }

    private fun generateCards(level: Int): List<Card> {
        val selectedContents = contents.shuffled().take(level)
        val cards = (selectedContents + selectedContents).shuffled().mapIndexed { index, content ->
            Card(id = index, content = content)
        }
        return cards
    }

    fun resetGame() {
        currentLevel = 2
        state = MemoramaState(cards = generateCards(currentLevel))
    }

    fun nextLevel() {
        if (currentLevel < 16 && currentLevel < contents.size) {
            currentLevel++
        }
        state = MemoramaState(cards = generateCards(currentLevel))
    }

    fun onCardSelected(card: Card) {
        if (state.isProcessing || card.isFaceUp || card.isMatched) {
            return
        }

        if (state.selectedCardId == null) {
            state = state.copy(selectedCardId = card.id, cards = state.cards.map {
                if (it.id == card.id) it.copy(isFaceUp = true) else it
            })
        } else {
            val selectedCard = state.cards.first { it.id == state.selectedCardId }
            if (selectedCard.content == card.content) {
                state = state.copy(
                    selectedCardId = null,
                    pairsFound = state.pairsFound + 1,
                    cards = state.cards.map {
                        if (it.id == card.id || it.id == selectedCard.id) it.copy(isFaceUp = true, isMatched = true)
                        else it
                    }
                )
                if (state.cards.all { it.isMatched }) {
                    state = state.copy(isProcessing = true)
                }
            } else {
                state = state.copy(
                    isProcessing = true,
                    cards = state.cards.map {
                        if (it.id == card.id || it.id == selectedCard.id) it.copy(isFaceUp = true) else it
                    }
                )

                viewModelScope.launch {
                    delay(1000L)
                    state = state.copy(
                        selectedCardId = null,
                        isProcessing = false,
                        cards = state.cards.map {
                            if (it.id == card.id || it.id == selectedCard.id) it.copy(isFaceUp = false) else it
                        }
                    )
                }
            }
        }
    }
}