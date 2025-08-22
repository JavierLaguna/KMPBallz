package dev.jlaguna.kmpballz.ui.scenes.charactersList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jlaguna.kmpballz.business.useCases.GetCharactersUseCase
import dev.jlaguna.kmpballz.business.models.Character
import dev.jlaguna.kmpballz.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class CharactersListViewModel : ViewModel(), KoinComponent {

    private val getCharactersUseCase: GetCharactersUseCase by inject()

    var characters = MutableStateFlow(UIState<List<Character>>())
        private set

    fun handleEvent(event: CharactersListContract.Event) {
        when (event) {
            is CharactersListContract.Event.OnLoadView -> getCharacters()
            is CharactersListContract.Event.OnEndScroll -> loadMoreCharacters()
            is CharactersListContract.Event.OnPressRetry -> getCharacters()
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            val currentCharacters = characters.value.data ?: emptyList()
            characters.value = characters.value.setLoading()

            try {
                val newCharacters = getCharactersUseCase.getCharacters()

                if (newCharacters.isNotEmpty()) {
                    val updatedCharacters = currentCharacters + newCharacters
                    characters.value = characters.value.setPopulated(updatedCharacters)
                }
            } catch (e: Exception) {
                characters.value = characters.value.setError(e)
            }
        }
    }

    private fun loadMoreCharacters() {
        if (characters.value.state == UIState.State.LOADING || characters.value.data == null) return
        getCharacters()
    }
}
