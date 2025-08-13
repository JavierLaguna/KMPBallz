package dev.jlaguna.kmpballz.ui.scenes.charactersList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jlaguna.kmpballz.data.models.Character
import dev.jlaguna.kmpballz.data.repositories.CharactersRepository
import dev.jlaguna.kmpballz.ui.StateLoadable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CharactersListViewModel : ViewModel(), KoinComponent {
    private val repository: CharactersRepository by inject()

    var characters = MutableStateFlow(StateLoadable<List<Character>>())
        private set

    fun onUiReady() {
        getCharacters()
    }

    fun loadMoreCharacters() {
        if (characters.value.isLoading || characters.value.data == null) return
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            val currentCharacters = characters.value.data ?: emptyList()
            characters.value = characters.value.update(StateLoadable.State.Loading())
            val newCharacters = repository.fetchCharacters()

            if (newCharacters.isNotEmpty()) {
                val updatedCharacters = currentCharacters + newCharacters
                characters.value = characters.value.update(StateLoadable.State.Populated(updatedCharacters))
            } else {
                characters.value = StateLoadable(StateLoadable.State.Empty())
            }
        }
    }
}
