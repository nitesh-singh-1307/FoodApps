package com.example.foodapps.prasentation.signinscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapps.domain.usecases.login_case.LoginInvokeCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.foodapps.domain.repository.Result

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginInvokeCase
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            when (val result = loginUseCase(email, password)) {
                is Result.Success -> {
                    _loginState.value = LoginState.Success(result.data)
                }
                is Result.Error -> {
                    _loginState.value = LoginState.Error(result.message)
                }
            }
        }
    }

}