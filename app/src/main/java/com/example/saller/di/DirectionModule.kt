package com.example.saller.di

import com.example.saller.presentor.homeScreen.ProductDirection
import com.example.saller.presentor.homeScreen.ProductDirectionImpl
import com.example.saller.presentor.login.LoginDirection
import com.example.saller.presentor.login.LoginDirectionIMpl
import com.example.saller.presentor.splash.SplashDirection
import com.example.saller.presentor.splash.SplashDirectionImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DirectionModule {


    @[Binds Singleton]
    fun bindProduct(impl: ProductDirectionImpl): ProductDirection

    @[Binds Singleton]
    fun bindSplash(impl: SplashDirectionImpl): SplashDirection

    @[Binds Singleton]
    fun bindLogin(impl: LoginDirectionIMpl): LoginDirection
}