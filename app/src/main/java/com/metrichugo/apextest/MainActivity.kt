package com.metrichugo.apextest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.metrichugo.apextest.databinding.ActivityMainBinding
import com.metrichugo.apextest.ui.view.PokedexAdapter
import com.metrichugo.apextest.ui.view.PokemonInfo
import com.metrichugo.apextest.ui.viewmodel.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PokedexAdapter
    private val pokedexViewModel: PokedexViewModel by viewModels()
    private var isLoading = false
    val pokemonInfo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pokedexViewModel.onCreate()
        initRecyclerView()

        pokedexViewModel._pokemonList.observe(this, Observer {
            adapter.updateData(it)
            isLoading = false
        })
        pokedexViewModel._navigateToPokemonDetail.observe(this, Observer {
            it?.let {
                doNavigationIntent(it)
                pokedexViewModel.onPokemonInfoNavigated()
            }
        })
    }

    private fun initRecyclerView() {
        adapter = PokedexAdapter(PokedexAdapter.ClickListener { name ->
            pokedexViewModel.onPokemonInfoNavigate(name)
        })
        val layoutManager = LinearLayoutManager(this)
        binding.pokemonList.layoutManager = layoutManager
        binding.pokemonList.adapter = adapter
        binding.pokemonList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    pokedexViewModel.loadMoreResults(layoutManager.itemCount)
                    isLoading = true
                }
            }
        })
    }

    private fun doNavigationIntent(name: String) {
        val pokemonInfoIntent = Intent(applicationContext, PokemonInfo::class.java)
        pokemonInfoIntent.putExtra("pokemon_name", name)
        pokemonInfo.launch(pokemonInfoIntent)
    }
}