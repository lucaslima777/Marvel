package lln.marvel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lln.marvel.model.CharacterRepository

class ViewModelFactory(private val repository: CharacterRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharacterViewModel(repository) as T
    }
}