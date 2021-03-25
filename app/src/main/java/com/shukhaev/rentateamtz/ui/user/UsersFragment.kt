package com.shukhaev.rentateamtz.ui.user

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.shukhaev.rentateamtz.R
import com.shukhaev.rentateamtz.data.User
import com.shukhaev.rentateamtz.databinding.FragmentUsersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users), UsersAdapter.OnListItemClickListener {
    private val viewModel: UsersViewModel by viewModels()
    private val usersAdapter = UsersAdapter(this)
    private lateinit var binding: FragmentUsersBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersBinding.bind(view)
        setupRecyclerView()
        binding.fabRefresh.setOnClickListener {
            viewModel.getUsers()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.usersFlow.collect {
                usersAdapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.stateEvents.collect { event ->
                when (event) {
                    is UsersViewModel.StateEvents.Error -> {
                        showLoading(false)
                        Snackbar.make(requireView(), event.message, Snackbar.LENGTH_SHORT).show()
                    }
                    UsersViewModel.StateEvents.Loading -> {
                        showLoading(true)
                    }
                    is UsersViewModel.StateEvents.NavigateToDetail -> {
                        val action =
                            UsersFragmentDirections.actionNavigationUsersToUserDetailFragment(event.user)
                        findNavController().navigate(action)
                    }
                    is UsersViewModel.StateEvents.Success -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progress.isVisible = isLoading
    }

    private fun setupRecyclerView() {
        binding.rvUsers.apply {
            adapter = usersAdapter
            setHasFixedSize(true)
        }
    }

    override fun onListItemClicked(user: User) {
        viewModel.itemClicked(user)
    }
}