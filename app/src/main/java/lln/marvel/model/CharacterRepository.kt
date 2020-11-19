package lln.marvel.model

import lln.marvel.data.OperationCallback
import lln.marvel.model.character.ResponseCharacterModel

class CharacterRepository(private val characterDataSource: CharacterDataSource) {
    fun fetchCharacter(callback: OperationCallback<ResponseCharacterModel>) {
        characterDataSource.retrieveCharacter(callback)
    }

    fun cancel() {
        characterDataSource.cancel()
    }
}