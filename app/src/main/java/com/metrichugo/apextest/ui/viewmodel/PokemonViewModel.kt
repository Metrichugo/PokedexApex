package com.metrichugo.apextest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metrichugo.apextest.data.model.Pokemon
import com.metrichugo.apextest.domain.GetPokemonInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase,
) : ViewModel() {

    val _pokemon = MutableLiveData<Pokemon>()

    fun onCreate(name: String) {
        getPokemonInfo(name)
    }

    private fun getPokemonInfo(name: String) {
        viewModelScope.launch {
            val result = getPokemonInfoUseCase.getPokemonInfo(name)
            _pokemon.postValue(result)
        }
    }
}