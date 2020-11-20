package lln.marvel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lln.marvel.data.OperationCallback
import lln.marvel.model.CharacterRepository
import lln.marvel.model.character.ResponseCharacterModel
import lln.marvel.state.State

open class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _state = MutableLiveData<State<ResponseCharacterModel>>()
    val state: LiveData<State<ResponseCharacterModel>> = _state

    fun loadCharacter() {
        _state.value = State.InProgress(isLoading = true)
        val listener = object : OperationCallback<ResponseCharacterModel> {
            override fun onError(error: String?) {
                _state.value = State.Error(error)
                _state.value = State.InProgress(isLoading = false)
            }

            override fun onSuccess(data: ResponseCharacterModel) {
                _state.value = State.Success(data)
                _state.value = State.InProgress(isLoading = false)
            }
        }

        repository.fetchCharacter(listener)
    }
}