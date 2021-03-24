package com.shukhaev.rentateamtz.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shukhaev.rentateamtz.data.User
import com.shukhaev.rentateamtz.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersViewModel @ViewModelInject constructor(val repo: Repository) : ViewModel() {

    private val _stateEvents = MutableStateFlow<StateEvents>(StateEvents.Empty)
    val stateEvents: StateFlow<StateEvents> = _stateEvents

    //    private val _users = MutableLiveData<List<User>>()
//    val users:LiveData<List<User>> = _users


    val usersFlow = repo.getUsersFromDb()

    init {
        _stateEvents.value = StateEvents.Loading
        viewModelScope.launch { repo.getUsersFromApi() }
    }

    fun itemClicked(user: User) {
        _stateEvents.value = StateEvents.NavigateToDetail(user)
    }

    sealed class StateEvents {
        object Empty : StateEvents()
        object Loading : StateEvents()
        data class Error(val message: String) : StateEvents()
        data class Success(val message: String) : StateEvents()
        data class NavigateToDetail(val user: User) : StateEvents()
    }
}