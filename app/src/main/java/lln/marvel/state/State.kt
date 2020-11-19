package lln.marvel.state

sealed class State<out R> {
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val exception: String?) : State<Nothing>()
    data class InProgress(val isLoading: Boolean) : State<Nothing>()
}