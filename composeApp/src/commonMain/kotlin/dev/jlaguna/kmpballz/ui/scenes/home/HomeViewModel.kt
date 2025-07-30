package dev.jlaguna.kmpballz.ui.scenes.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jlaguna.kmpballz.data.models.Character
import dev.jlaguna.kmpballz.data.remote.CharacterService
import dev.jlaguna.kmpballz.data.repositories.CharactersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(val service: CharacterService): ViewModel() {

    private val repository = CharactersRepository(service)

    var state = MutableStateFlow(UiState())
        private set

    fun onUiReady() {
        viewModelScope.launch {
            state.value = UiState(isLoading = true)
            val char = repository.fetchCharacterById(1)
            state.value = UiState(isLoading = false, character = char)
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val character: Character? = null
    )
}