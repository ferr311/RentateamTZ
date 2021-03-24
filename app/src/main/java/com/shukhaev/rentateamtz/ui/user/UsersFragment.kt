package com.shukhaev.rentateamtz.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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

        lifecycleScope.launchWhenStarted {
            viewModel.usersFlow.collect {
                usersAdapter.submitList(it)
            }
        }
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