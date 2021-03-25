package com.shukhaev.rentateamtz.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shukhaev.rentateamtz.data.User
import com.shukhaev.rentateamtz.network.Resource
import com.shukhaev.rentateamtz.repository.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UsersViewModel @ViewModelInject constructor(private val repo: Repository) : ViewModel() {

    private val _stateEvents = Channel<StateEvents>()
    val stateEvents = _stateEvents.receiveAsFlow()

    val usersFlow = repo.getUsersFromDb()

    init {
        getUsers()
    }

    fun getUsers() = viewModelScope.launch {
        _stateEvents.send(StateEvents.Loading)
        when (val loadResult = repo.getUsersFromApi()) {
            is Resource.Error -> {
                _stateEvents.send(StateEvents.Error(loadResult.message.toString()))
            }
            is Resource.Success -> {
                _stateEvents.send(StateEvents.Success)
            }
        }
    }

    fun itemClicked(user: User) = viewModelScope.launch {
        _stateEvents.send(StateEvents.NavigateToDetail(user))
    }

    sealed class StateEvents {
        object Loading : StateEvents()
        data class Error(val message: String) : StateEvents()
        object Success : StateEvents()
        data class NavigateToDetail(val user: User) : StateEvents()
    }
}