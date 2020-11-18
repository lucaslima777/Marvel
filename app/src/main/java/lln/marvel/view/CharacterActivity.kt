package lln.marvel.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import lln.marvel.R
import lln.marvel.util.custom.BaseActivity
import lln.marvel.model.character.CharacterModel
import lln.marvel.di.MarvelProvider
import lln.marvel.state.State
import lln.marvel.viewmodel.CharacterViewModel

class CharacterActivity : BaseActivity() {

    private lateinit var viewModel: CharacterViewModel

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
        viewModel = ViewModelProvider(
            this,
            MarvelProvider.provideViewModelFactory()
        ).get(CharacterViewModel::class.java)

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