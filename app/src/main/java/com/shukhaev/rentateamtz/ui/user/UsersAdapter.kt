package com.shukhaev.rentateamtz.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shukhaev.rentateamtz.data.User
import com.shukhaev.rentateamtz.databinding.ItemUsersListBinding

class UsersAdapter(private val listener: OnListItemClickListener) :
    ListAdapter<User, UsersAdapter.UserViewHolder>(UserComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUsersListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(user)
        }
    }

    inner class UserViewHolder(private val binding: ItemUsersListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val user = getItem(adapterPosition)
                if (user != null) {
                    listener.onListItemClicked(user)
                }
            }
        }

        fun bind(user: User) {
            binding.apply {
                tvFirstName.text = user.firstName
                tvLastName.text = user.lastName
            }
        }
    }

    class UserComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem

    }

    interface OnListItemClickListener {
        fun onListItemClicked(user: User)
    }

}