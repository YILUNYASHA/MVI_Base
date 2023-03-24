package com.example.myapplication.features.helloworld

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.myapplication.R
import com.example.myapplication.core.BaseFragment
import com.example.myapplication.core.MvRxViewModel
import com.example.myapplication.databinding.HelloWorldFragmentBinding
import com.example.myapplication.viewbinding.viewBinding

data class HelloWorldState(val title: String = "Hello World") : MavericksState

class HelloWorldViewModel(initialState: HelloWorldState) : MvRxViewModel<HelloWorldState>(initialState)

class HelloWorldFragment : BaseFragment(R.layout.hello_world_fragment) {
    private val binding: HelloWorldFragmentBinding by viewBinding()
    private val viewModel: HelloWorldViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setupWithNavController(findNavController())
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.marquee.setTitle(state.title)
    }
}
