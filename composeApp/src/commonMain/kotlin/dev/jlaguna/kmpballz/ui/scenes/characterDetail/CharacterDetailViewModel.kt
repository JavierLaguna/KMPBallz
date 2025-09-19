package dev.jlaguna.kmpballz.ui.scenes.characterDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jlaguna.kmpballz.business.models.Character
import dev.jlaguna.kmpballz.business.useCases.GetCharacterDetailUseCase
import dev.jlaguna.kmpballz.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CharacterDetailViewModel(
    private val characterId: Int
) : ViewModel(), KoinComponent {

    private val getCharacterDetailUseCase: GetCharacterDetailUseCase by inject()

    var character = MutableStateFlow(UIState<Character>())
        private set

    init {
        getCharacter()
    }

    fun handleEvent(event: CharacterDetailContract.Event) {
        when (event) {
            is CharacterDetailContract.Event.OnPressRetry -> getCharacter()
        }
    }

    private fun getCharacter() {
        viewModelScope.launch {
            character.value = character.value.setLoading()

            try {
                val characterDetail = getCharacterDetailUseCase.execute(characterId)
                character.value = character.value.setPopulated(characterDetail)
            } catch (e: Exception) {
                character.value = character.value.setError(e)
            }
        }
    }
}