package com.example.saller.presentor.splash

import com.example.saller.presentor.homeScreen.ProductScreen
import com.example.saller.presentor.login.LoginScreen
import com.example.saller.utils.navigation.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface SplashDirection {
    suspend fun moveToLogin()
    suspend fun moveToProductScreen()
}


@Singleton
class SplashDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : SplashDirection {
    override suspend fun moveToLogin() {
        appNavigator.replaceScreen(LoginScreen())
    }

    override suspend fun moveToProductScreen() {
        appNavigator.addScreen(ProductScreen())
    }
}