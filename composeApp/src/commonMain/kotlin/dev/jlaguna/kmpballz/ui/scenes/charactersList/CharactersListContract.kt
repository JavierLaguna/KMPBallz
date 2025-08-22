package dev.jlaguna.kmpballz.ui.scenes.charactersList

import dev.jlaguna.kmpballz.ui.UIEvent

interface CharactersListContract {

    sealed interface Event: UIEvent {
        data object OnLoadView: Event
        data object OnEndScroll: Event
        data object OnPressRetry: Event
    }
}