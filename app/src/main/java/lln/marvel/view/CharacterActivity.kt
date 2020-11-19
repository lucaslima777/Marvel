package lln.marvel.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import lln.marvel.R
import lln.marvel.model.character.CharacterModel
import lln.marvel.state.State
import lln.marvel.viewmodel.CharacterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterActivity : AppCompatActivity() {

    val viewModel: CharacterViewModel by viewModel()

    private lateinit var tvResponse: TextView
    private lateinit var tvError: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        tvResponse = findViewById(R.id.tv_response)
        tvError = findViewById(R.id.tv_error)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun observeViewModel() {
        viewModel.state.observe(this, Observer {
            when (it) {
                is State.Success -> renderCharacters(it.data.data.results)
                is State.Error -> tvError.text = it.exception
                is State.InProgress -> loading(it.isLoading)
            }
        })
    }

    private fun renderCharacters(characterList: List<CharacterModel>) {
        tvResponse.text = characterList.toString()
    }

    private fun loading(isShow: Boolean) {
        progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCharacter()
    }
}