package com.example.myapplication.features.parentfragment

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.parentFragmentViewModel
import com.airbnb.mvrx.withState
import com.example.myapplication.R
import com.example.myapplication.core.BaseFragment
import com.example.myapplication.databinding.ChildFragmentBinding
import com.example.myapplication.viewbinding.viewBinding

class ChildFragment : BaseFragment(R.layout.child_fragment) {
    private val binding: ChildFragmentBinding by viewBinding()
    private val viewModel: ParentChildSharedViewModel by parentFragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.root.setOnClickListener {
            viewModel.incrementCount()
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.text.text = "ChildFragment: Count: ${state.count}"
    }
}
