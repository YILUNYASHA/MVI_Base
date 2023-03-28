package com.example.myapplication.features.dadjoke

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.*
import com.example.myapplication.R
import com.example.myapplication.core.BaseFragment
import com.example.myapplication.databinding.DadJokeIndexFragmentBinding
import com.example.myapplication.viewbinding.viewBinding
import com.example.myapplication.views.basicRow
import com.example.myapplication.views.loadingRow
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest

class DadJokeIndexFragment : BaseFragment(R.layout.dad_joke_index_fragment) {
    private val binding: DadJokeIndexFragmentBinding by viewBinding()
    private val viewModel: DadJokeIndexViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Unique only will prevent the snackbar from showing again if the user rotates the screen or returns to this fragment.
        viewModel.onAsync(
            DadJokeIndexState::request, uniqueOnly(),
            onFail = {
                Snackbar.make(binding.root, "Jokes request failed.", Snackbar.LENGTH_INDEFINITE).show()
            },
            onSuccess = {},
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setupWithNavController(findNavController())
        binding.recyclerView.buildModelsWith(object : EpoxyRecyclerView.ModelBuilderCallback {
            override fun buildModels(controller: EpoxyController) {
                controller.buildModels()
            }
        })
        binding.toolbar.setOnClickListener { viewModel.hideRows() }
    }

    override fun invalidate() {
        binding.recyclerView.requestModelBuild()
    }

    private fun EpoxyController.buildModels() = withState(viewModel) { state ->
        state.jokes.forEach { joke ->
            basicRow {
                id(joke.id)
                title(joke.joke)
                clickListener { v-> }
            }
        }

        if (state.isShowRows)
            loadingRow {
                // Changing the ID will force it to rebind when new data is loaded even if it is
                // still on screen which will ensure that we trigger loading again.
                id("loading${state.jokes.size}")
                onBind { _, _, _ -> viewModel.fetchNextPage() }
            }
    }

}
