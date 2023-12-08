package com.example.saller.presentor.login

import com.example.saller.presentor.homeScreen.ProductScreen
import com.example.saller.utils.navigation.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface LoginDirection {
  suspend fun moveToEntryScreen()
}

@Singleton
class LoginDirectionIMpl @Inject constructor(
    val appNavigator: AppNavigator
): LoginDirection {
    override suspend fun moveToEntryScreen() {
        appNavigator.replaceScreen(ProductScreen())
    }


}