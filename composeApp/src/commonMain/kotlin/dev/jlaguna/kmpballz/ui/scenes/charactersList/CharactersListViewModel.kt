package dev.jlaguna.kmpballz.ui.scenes.charactersList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jlaguna.kmpballz.data.models.Character
import dev.jlaguna.kmpballz.data.repositories.CharactersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CharactersListViewModel : ViewModel(), KoinComponent {

    data class UiState(
        val isLoading: Boolean = false,
        val characters: List<Character> = emptyList()
    )

    private val repository: CharactersRepository by inject()

    var state = MutableStateFlow(UiState())
        private set

    fun onUiReady() {
        viewModelScope.launch {
            state.value = UiState(isLoading = true)
            val characters = repository.fetchCharacters()
            state.value = UiState(characters = characters)
        }
    }
}