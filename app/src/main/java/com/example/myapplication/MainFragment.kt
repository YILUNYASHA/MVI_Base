package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.databinding.MainFragmentBinding
import com.example.myapplication.viewbinding.viewBinding
import com.example.myapplication.views.basicRow
import com.example.myapplication.views.marquee

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding: MainFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.withModels {
            marquee {
                id("marquee")
                title("Welcome to Mavericks")
                subtitle("Select a demo below")
            }

            basicRow {
                id("hello_world")
                title("Hello World")
                subtitle(demonstrates("Simple Mavericks usage"))
                clickListener { _ -> findNavController().navigate(R.id.action_main_to_helloWorldFragment) }
            }

            basicRow {
                id("parent_fragments")
                title("Parent/Child ViewModel")
                subtitle(demonstrates("parentFragmentViewModel"))
                clickListener { _ -> findNavController().navigate(R.id.action_main_to_parentFragment) }
            }

            basicRow {
                id("random_dad_joke")
                title("Random Dad Joke")
                subtitle(demonstrates("fragmentViewModel", "Network requests", "Dependency Injection"))
                clickListener { _ -> findNavController().navigate(R.id.action_main_to_randomDadJokeFragment) }
            }

            basicRow {
                id("dad_jokes")
                title("Dad Jokes")
                subtitle(
                    demonstrates(
                        "fragmentViewModel",
                        "Fragment arguments",
                        "Network requests",
                        "Pagination",
                        "Dependency Injection"
                    )
                )
                clickListener { _ -> findNavController().navigate(R.id.action_mainFragment_to_dadJokeIndex) }
            }

            basicRow {
                id("user_flow")
                title("User Flow")
                subtitle(
                    demonstrates(
                        "Sharing data across screens",
                        "activityViewModel and existingViewModel"
                    )
                )
                clickListener { _ -> findNavController().navigate(R.id.action_main_to_flowIntroFragment) }
            }
        }
    }

    private fun demonstrates(vararg items: String) =
        arrayOf("Demonstrates:", *items).joinToString("\n\t\t• ")
}
