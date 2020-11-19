package lln.marvel.data

interface OperationCallback<T> {
    fun onSuccess(data: T)
    fun onError(error: String?)
}