package com.example.myapplication.features.flow

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.activityViewModel
import com.example.myapplication.R
import com.example.myapplication.core.BaseFragment
import com.example.myapplication.databinding.FlowIntroFragmentBinding
import com.example.myapplication.viewbinding.viewBinding
import com.example.myapplication.views.basicRow
import com.example.myapplication.views.marquee

class FlowIntroFragment : BaseFragment(R.layout.flow_intro_fragment) {
    private val binding: FlowIntroFragmentBinding by viewBinding()
    private val viewModel: FlowViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setupWithNavController(findNavController())
        binding.recyclerView.withModels {
            marquee {
                id("marquee")
                title("Intro")
                subtitle("Set the initial counter value")
            }

            arrayOf(0, 10, 50, 100, 1_000, 10_000).forEach { count ->
                basicRow {
                    id(count)
                    title("$count")
                    clickListener { _ ->
                        viewModel.setCount(count)
                        findNavController().navigate(R.id.action_flowIntroFragment_to_flowCounterFragment)
                    }
                }
            }
        }
    }

    override fun invalidate() {
        // Do nothing.
    }
}
