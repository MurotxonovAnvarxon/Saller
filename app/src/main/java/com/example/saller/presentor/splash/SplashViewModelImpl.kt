package com.example.saller.presentor.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saller.data.local.myPref.MyPref
import com.example.saller.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val direction: SplashDirection,
    private val repository: AppRepository,
    private val pref: MyPref
) : ViewModel() {
    init {
        viewModelScope.launch {
                if (pref.isLogin()) {
                    delay(1000L)
                    direction.moveToProductScreen()
                } else {
                    delay(1500L)
                    direction.moveToLogin()
                }
        }
    }
}