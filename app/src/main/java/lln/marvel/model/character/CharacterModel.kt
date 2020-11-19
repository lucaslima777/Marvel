package lln.marvel.model.character

import com.google.gson.annotations.SerializedName

data class CharacterModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String
)