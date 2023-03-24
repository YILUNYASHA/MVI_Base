package com.example.myapplication.features.parentfragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.myapplication.R
import com.example.myapplication.core.BaseFragment
import com.example.myapplication.databinding.ParentFragmentBinding
import com.example.myapplication.viewbinding.viewBinding

class ParentFragment : BaseFragment(R.layout.parent_fragment) {
    private val binding: ParentFragmentBinding by viewBinding()
    private val viewModel: ParentChildSharedViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setupWithNavController(findNavController())
        binding.root.setOnClickListener { viewModel.incrementCount() }
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.textView.text = "ParentFragment: Count: ${state.count}"
    }
}
