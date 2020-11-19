package lln.marvel.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import lln.marvel.R
import lln.marvel.model.character.CharacterModel
import lln.marvel.state.State
import lln.marvel.viewmodel.CharacterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterActivity : AppCompatActivity() {

    val viewModel: CharacterViewModel by viewModel()
    private lateinit var recyclerviewCharacter: RecyclerView
    private lateinit var shimmerLayout: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        recyclerviewCharacter = findViewById(R.id.recyclerviewCharacter)
        shimmerLayout = findViewById(R.id.shimmerFrameLayout)
    }

    private fun setupAdapter(listCharacterModel: List<CharacterModel>) {
        recyclerviewCharacter.layoutManager = LinearLayoutManager(applicationContext)
        recyclerviewCharacter.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerviewCharacter.adapter = CharacterAdapter(listCharacterModel, applicationContext)
    }

    private fun observeViewModel() {
        viewModel.state.observe(this, Observer {
            when (it) {
                is State.Success -> setupAdapter(it.data.characterModel.results)
                is State.Error -> loading(true)
                is State.InProgress -> loading(it.isLoading)
            }
        })
    }

    private fun loading(isShow: Boolean) = if (isShow) {
        shimmerLayout.startShimmer()
    } else {
        shimmerLayout.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCharacter()
    }
}