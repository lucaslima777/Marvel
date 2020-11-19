package lln.marvel.di

import lln.marvel.data.CharacterRemoteDataSource
import lln.marvel.data.RetrofitClient
import lln.marvel.model.CharacterDataSource
import lln.marvel.model.CharacterRepository
import lln.marvel.viewmodel.CharacterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitClient() }
    single { CharacterRepository(get()) }
    single<CharacterDataSource> { CharacterRemoteDataSource(get()) }

    viewModel { CharacterViewModel(get()) }
}