package com.shukhaev.rentateamtz.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shukhaev.rentateamtz.repository.Repository

class UsersViewModel @ViewModelInject constructor(val repo: Repository) : ViewModel() {


}