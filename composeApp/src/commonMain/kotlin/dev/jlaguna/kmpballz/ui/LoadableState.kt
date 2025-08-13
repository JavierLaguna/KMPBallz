package dev.jlaguna.kmpballz.ui

data class StateLoadable<T : Any>(
    private var _state: State<T> = State.Initial()
) {
    sealed interface State<T> {
        class Initial<T> : State<T>
        class Loading<T> : State<T>
        data class Populated<T>(val data: T) : State<T>
        class Empty<T> : State<T>
        data class Error<T>(val error: Throwable) : State<T>
    }

    var state: State<T>
        get() = _state
        set(value) {
            _state = value
            stateDidUpdated()
        }

    var data: T? = null
        private set

    var error: Throwable? = null
        private set

    val isLoading: Boolean
        get() = state is State.Loading

    val isEmpty: Boolean
        get() = state is State.Empty

    init {
        stateDidUpdated()
    }

    private fun stateDidUpdated() {
        when (val s = state) {
            is State.Error -> {
                error = s.error
                data = null
            }
            is State.Populated -> {
                data = s.data
                error = null
            }
            else -> {
                data = null
                error = null
            }
        }
    }

    fun update(state: StateLoadable.State<T>): StateLoadable<T> {
        val a = StateLoadable<T>(state)
        a.data = data
        return a
    }
}

