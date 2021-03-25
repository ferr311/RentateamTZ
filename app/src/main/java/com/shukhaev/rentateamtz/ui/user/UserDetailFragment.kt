package com.shukhaev.rentateamtz.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.shukhaev.rentateamtz.R
import com.shukhaev.rentateamtz.databinding.FragmentUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {
    private lateinit var binding: FragmentUserDetailBinding
    private val args: UserDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserDetailBinding.bind(view)
        val user = args.user

        binding.apply {
            Glide.with(requireView()).load(user.avatar).into(ivAvatar)
            tvFirstName.text = user.firstName
            tvLastName.text = user.lastName
            tvEmail.text = user.email
        }
    }
}