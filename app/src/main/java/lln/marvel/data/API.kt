package lln.marvel.data

import lln.marvel.model.character.ResponseCharacterModel
import lln.marvel.util.MarvelConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("characters")
    fun allCharacters(
        @Query(MarvelConstants.PARAMS.LIMIT) limit: Int,
        @Query(MarvelConstants.PARAMS.OFFSET) offset: Int
    ): Call<ResponseCharacterModel>

}