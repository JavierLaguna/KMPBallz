package dev.jlaguna.kmpballz.ui.scenes.characterDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jlaguna.kmpballz.business.useCases.models.Character
import dev.jlaguna.kmpballz.data.repositories.CharactersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CharacterDetailViewModel(
    private val characterId: Int
) : ViewModel(), KoinComponent {

    data class UiState(
        val isLoading: Boolean = false,
        val character: Character? = null
    )

    private val repository: CharactersRepository by inject()

    var state = MutableStateFlow(UiState())
        private set

    init {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val character = repository.fetchCharacterById(characterId)
            state.value = UiState(false, character)
        }
    }
}