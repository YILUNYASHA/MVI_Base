package com.example.myapplication.features.flow

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.existingViewModel
import com.airbnb.mvrx.withState
import com.example.myapplication.R
import com.example.myapplication.core.BaseFragment
import com.example.myapplication.databinding.FlowCounterFragmentBinding
import com.example.myapplication.viewbinding.viewBinding

class FlowCounterFragment : BaseFragment(R.layout.flow_counter_fragment) {
    private val binding: FlowCounterFragmentBinding by viewBinding()

    /**
     * Because we know that this Fragment isn't the first Fragment in the flow and that the ViewModel
     * will have been created by an earlier screen in the Fragment, we can use [existingViewModel]
     * which will throw an exception if it hasn't been created yet.
     *
     * This is useful if your ViewModel has dependencies for its initial state or anything else.
     */
    private val viewModel by existingViewModel(FlowViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setupWithNavController(findNavController())
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.marquee.setTitle("Counter ${state.count}")
    }
}
