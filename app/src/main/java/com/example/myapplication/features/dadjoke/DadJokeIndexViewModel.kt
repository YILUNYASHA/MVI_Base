package com.example.myapplication.features.dadjoke

import com.airbnb.mvrx.*
import com.airbnb.mvrx.sample.models.JokesResponse
import com.example.myapplication.core.MvRxViewModel
import com.example.myapplication.models.Joke
import com.example.myapplication.network.RemoteService
import org.koin.android.ext.android.inject

const val JOKES_PER_PAGE = 5

data class DadJokeIndexState(
    /** We use this request to store the list of all jokes. */
    val jokes: List<Joke> = emptyList(),
    /** We use this Async to keep track of the state of the current network request. */
    val request: Async<JokesResponse> = Uninitialized,
    val isShowRows: Boolean = true
) : MavericksState

/**
 * initialState *must* be implemented as a constructor parameter.
 */
class DadJokeIndexViewModel(
    initialState: DadJokeIndexState,
    private val dadJokeService: RemoteService
) : MvRxViewModel<DadJokeIndexState>(initialState) {

    init {
        fetchNextPage()
    }

    // 防止重复请求
    fun fetchNextPage() = withState { state ->
        if (state.request is Loading) return@withState

        suspend {
            dadJokeService.search(page = state.jokes.size / JOKES_PER_PAGE + 1, limit = JOKES_PER_PAGE)
        }.execute { copy(request = it, jokes = jokes + (it()?.results ?: emptyList())) }
    }

    fun hideRows() {
        setState {
            copy(isShowRows = false)
        }
    }

    /**
     * If you implement MavericksViewModelFactory in your companion object, MvRx will use that to create
     * your ViewModel. You can use this to achieve constructor dependency injection with Mavericks.
     *
     * @see MavericksViewModelFactory
     */
    companion object : MavericksViewModelFactory<DadJokeIndexViewModel, DadJokeIndexState> {

        override fun create(viewModelContext: ViewModelContext, state: DadJokeIndexState): DadJokeIndexViewModel {
            val service: RemoteService by viewModelContext.activity.inject()
            return DadJokeIndexViewModel(state, service)
        }
    }
}
