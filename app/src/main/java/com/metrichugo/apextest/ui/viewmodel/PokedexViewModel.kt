package com.metrichugo.apextest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metrichugo.apextest.data.model.NamedAPIResource
import com.metrichugo.apextest.domain.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
) : ViewModel() {

    val _pokemonList = MutableLiveData<List<NamedAPIResource>>()
    var _navigateToPokemonDetail = MutableLiveData<String?>()

    fun onCreate() {
        getPokemonList(offset = 0, limit = LIMIT)
    }

    fun loadMoreResults(offset: Int) {
        getPokemonList(offset, limit = LIMIT)
    }

    fun onPokemonInfoNavigate(name :String){
        _navigateToPokemonDetail.postValue(name)
    }

    fun onPokemonInfoNavigated(){
        _navigateToPokemonDetail.postValue(null)
    }

    private fun getPokemonList(offset: Int, limit: Int) {
        viewModelScope.launch {
            val result = getPokemonListUseCase.getPokemonList(offset, limit)
            if (result.isNotEmpty()) {
                _pokemonList.postValue(result)
            }
        }
    }

    companion object {
        const val LIMIT = 20
    }

}