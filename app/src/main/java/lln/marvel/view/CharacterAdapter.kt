package lln.marvel.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lln.marvel.R
import lln.marvel.model.character.CharacterModel
import lln.marvel.util.url

class CharacterAdapter(private val listCharacter: List<CharacterModel>,
                       private val context: Context
) :
    RecyclerView.Adapter<CharacterAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_character, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        (holder as ItemViewHolder).bindView(
            listCharacter[position].name,
            listCharacter[position].thumbnail.path.url(listCharacter[position].thumbnail.extension),
            context
        )
    }

    override fun getItemCount(): Int {
        return listCharacter.size
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivThumbnail: ImageView = view.findViewById(R.id.thumbnail_character)
        val tvName: TextView = view.findViewById(R.id.name_character)

        fun bindView(name: String, url: String, context: Context) {
            tvName.text = name
            Glide
                .with(context)
                .load(url)
                .centerCrop()
                .into(ivThumbnail);
        }
    }
}