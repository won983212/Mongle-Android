package com.won983212.mongle.presentation.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.won983212.mongle.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val viewModel by viewModels<FavoriteViewModel>()
    private lateinit var favoriteListAdapter: FavoriteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        favoriteListAdapter = FavoriteListAdapter(this) {
            viewModel.deleteFavorite(it)
        }

        binding.listFavorite.adapter = favoriteListAdapter
        binding.indicatorFavoritePagerPage.attachTo(binding.listFavorite)

        viewModel.favorites.observe(viewLifecycleOwner) {
            favoriteListAdapter.set(it)
        }

        // TODO suspend / coroutine 사용해서 무정지 logic구현
        viewModel.loadFavorites()

        return binding.root
    }
}