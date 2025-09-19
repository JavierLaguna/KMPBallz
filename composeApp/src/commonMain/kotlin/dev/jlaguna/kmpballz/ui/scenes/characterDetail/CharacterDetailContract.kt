package dev.jlaguna.kmpballz.ui.scenes.characterDetail

import dev.jlaguna.kmpballz.ui.UIEvent

interface CharacterDetailContract {

    sealed interface Event: UIEvent {
        data object OnPressRetry: Event
    }
}