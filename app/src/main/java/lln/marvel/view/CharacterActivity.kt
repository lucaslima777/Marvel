package lln.marvel.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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
    private lateinit var layoutError: ConstraintLayout
    private lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        recyclerviewCharacter = findViewById(R.id.recyclerviewCharacter)
        shimmerLayout = findViewById(R.id.shimmerFrameLayout)
        layoutError = findViewById(R.id.layout_error)
        tvError = findViewById(R.id.tv_error)
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
                is State.Error -> showError(it.exception)
                is State.InProgress -> loading(it.isLoading)
            }
        })
    }

    private fun loading(isShow: Boolean) {
        shimmerLayout.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showError(statusError: String?) {
        layoutError.visibility = View.VISIBLE
        tvError.text = statusError
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCharacter()
    }
}