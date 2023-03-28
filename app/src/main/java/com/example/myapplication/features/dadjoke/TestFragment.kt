package com.example.myapplication.features.dadjoke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.*
import com.example.myapplication.R
import com.example.myapplication.core.BaseFragment
import com.example.myapplication.databinding.FragmentTestBinding
import com.example.myapplication.databinding.RandomDadJokeFragmentBinding
import com.example.myapplication.viewbinding.viewBinding
import com.example.myapplication.views.basicRow
import com.example.myapplication.views.loadingRow
import com.example.myapplication.views.marquee


class TestFragment : BaseFragment(R.layout.fragment_test) {
    private val binding: FragmentTestBinding by viewBinding()
    private val viewModel: RandomDadJokeViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.buildModelsWith(object : EpoxyRecyclerView.ModelBuilderCallback {
            override fun buildModels(controller: EpoxyController) {
                controller.buildModels()
            }
        })
    }


    override fun invalidate() {
        binding.recycler.requestModelBuild()
    }

    private fun EpoxyController.buildModels() = withState(viewModel) { state ->
        marquee {
            id("marquee")
            title("Intro")
            subtitle("Set the initial counter value")
        }

        state.jokes.forEach { joke ->
            basicRow {
                id(joke.id)
                title(joke.joke)
            }
        }

        arrayOf(0, 10, 50, 100, 1_000, 10_000).forEach { count ->
            basicRow {
                id(count)
                title("$count")
            }
        }

        loadingRow {
            // Changing the ID will force it to rebind when new data is loaded even if it is
            // still on screen which will ensure that we trigger loading again.
            id("loading${state.jokes.size}")
            onBind { _, _, _ -> viewModel.fetchNextPage() }
        }
    }

}