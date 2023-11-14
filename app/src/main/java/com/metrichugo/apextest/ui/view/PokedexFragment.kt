package com.metrichugo.apextest.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.metrichugo.apextest.R
import com.metrichugo.apextest.databinding.PokedexFragmentBinding
import com.metrichugo.apextest.ui.viewmodel.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexFragment : Fragment() {

    private lateinit var binding: PokedexFragmentBinding
    private lateinit var adapter: PokedexAdapter
    private val pokedexViewModel: PokedexViewModel by viewModels()
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = PokedexFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokedexViewModel.onCreate()
        initRecyclerView()

        pokedexViewModel._pokemonList.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
            isLoading = false
        })
        pokedexViewModel._navigateToPokemonDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(PokedexFragmentDirections.actionPokedexFragmentToPokemonInfoFragment2(it))
                pokedexViewModel.onPokemonInfoNavigated()
            }
        })
    }

    private fun initRecyclerView() {
        adapter = PokedexAdapter(PokedexAdapter.ClickListener { name ->
            pokedexViewModel.onPokemonInfoNavigate(name)
        })
        val layoutManager = LinearLayoutManager(requireContext())
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

}