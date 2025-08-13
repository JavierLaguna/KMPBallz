package dev.jlaguna.kmpballz.ui

data class UIState<T>(
    val state: State = State.INITIAL,
    val data: T? = null,
    val error: Error? = null
) {
    enum class State {
        INITIAL,
        LOADING,
        POPULATED,
        EMPTY,
        ERROR
    }

    val isLoading: Boolean = state == State.LOADING
    val isEmpty: Boolean = state == State.EMPTY

    fun setLoading(): UIState<T> = UIState(State.LOADING, data, null)

    fun setPopulated(data: T?): UIState<T> {
        return if (data == null || (data as? List<*>)?.isEmpty() ?: false) {
            setEmpty()
        } else {
            UIState(State.POPULATED, data, null)
        }
    }

    fun setEmpty(): UIState<T> = UIState(State.EMPTY, null, null)

    fun setError(): UIState<T> = UIState(State.ERROR, null, error)
}

