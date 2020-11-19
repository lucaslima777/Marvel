package lln.marvel.model

import lln.marvel.data.OperationCallback
import lln.marvel.model.character.ResponseCharacterModel

interface CharacterDataSource {
    fun retrieveCharacter(callback: OperationCallback<ResponseCharacterModel>)
    fun cancel()
}