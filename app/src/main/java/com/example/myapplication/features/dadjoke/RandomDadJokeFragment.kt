package com.example.myapplication.features.dadjoke

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.*
import com.airbnb.mvrx.sample.models.JokesResponse
import com.example.myapplication.R
import com.example.myapplication.core.BaseFragment
import com.example.myapplication.core.MvRxViewModel
import com.example.myapplication.databinding.RandomDadJokeFragmentBinding
import com.example.myapplication.models.Joke
import com.example.myapplication.network.RemoteService
import com.example.myapplication.viewbinding.viewBinding
import org.koin.android.ext.android.inject

data class RandomDadJokeState(
    val joke: Async<Joke> = Uninitialized,
    /** We use this request to store the list of all jokes. */
    val jokes: List<Joke> = emptyList(),
    /** We use this Async to keep track of the state of the current network request. */
    val request: Async<JokesResponse> = Uninitialized
) : MavericksState

class RandomDadJokeViewModel(initialState: RandomDadJokeState, private val dadJokeService: RemoteService) :
    MvRxViewModel<RandomDadJokeState>(initialState) {
    init {
        fetchRandomJoke()
        fetchNextPage()
    }

    fun fetchRandomJoke() {
        suspend {
            dadJokeService.random()
        }.execute { copy(joke = it) }
    }

    fun fetchNextPage() = withState { state ->
        suspend {
            dadJokeService.search(page = state.jokes.size / JOKES_PER_PAGE + 1, limit = JOKES_PER_PAGE)
        }.execute { copy(request = it, jokes = jokes + (it()?.results ?: emptyList())) }
    }

    companion object : MavericksViewModelFactory<RandomDadJokeViewModel, RandomDadJokeState> {

        override fun create(viewModelContext: ViewModelContext, state: RandomDadJokeState): RandomDadJokeViewModel {
            val service: RemoteService by viewModelContext.activity.inject()
            return RandomDadJokeViewModel(state, service)
        }
    }
}

class RandomDadJokeFragment : BaseFragment(R.layout.random_dad_joke_fragment) {
    private val binding: RandomDadJokeFragmentBinding by viewBinding()
    private val viewModel: RandomDadJokeViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setupWithNavController(findNavController())
        binding.randomizeButton.setOnClickListener {
//            viewModel.fetchRandomJoke()
            findNavController().navigate(R.id.action_randomDadJokeFragment_to_testFragment)
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.loader.isVisible = state.joke is Loading
        binding.row.isVisible = state.joke is Success
        binding.row.setTitle(state.joke()?.joke+"  jokes的大小${state.jokes.size}")


    }
}
