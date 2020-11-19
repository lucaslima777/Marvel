package lln.marvel.data

import lln.marvel.model.CharacterDataSource
import lln.marvel.model.character.ResponseCharacterModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterRemoteDataSource(apiClient: RetrofitClient) : CharacterDataSource {

    private var call: Call<ResponseCharacterModel>? = null
    private val service = apiClient.build()

    override fun retrieveCharacter(callback: OperationCallback<ResponseCharacterModel>) {
        call = service?.allCharacters(20,0)
        call?.enqueue(object : Callback<ResponseCharacterModel> {
            override fun onFailure(call: Call<ResponseCharacterModel>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<ResponseCharacterModel>,
                response: Response<ResponseCharacterModel>
            ) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it)
                    } else {
                        callback.onError(it.etag)
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}