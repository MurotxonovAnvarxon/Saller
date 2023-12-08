package com.example.saller.presentor.login

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saller.data.local.myPref.MyPref
import com.example.saller.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val appRepository: AppRepository,
    private val loginDirection: LoginDirection,
    private val pref: MyPref
) : ViewModel(), LoginContract.ViewModel {
    override val uiState = MutableStateFlow(LoginContract.UIState())

    override fun onEventDispatcher(intent: LoginContract.Intent) {
        when (intent) {
            is LoginContract.Intent.OnLogin -> {
                viewModelScope.launch {
                    uiState.update { it.copy(loading = true) }
                    appRepository.login(intent.sellerName, intent.password)
                        .onStart { uiState.update { it.copy(loading = true) } }
                        .onCompletion { uiState.update { it.copy(loading = false) } }
                        .onEach {
                            it.onSuccess {
                                pref.saveUserName(intent.sellerName)
                                pref.setLogin(true)
                                loginDirection.moveToEntryScreen()
                            }
                            it.onFailure {
                                Toast.makeText(
                                    intent.context,
                                    it.message?:"Something wrong",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                            uiState.update { it.copy(loading = false) }
                        }.launchIn(viewModelScope)

                }
            }


        }
    }
}