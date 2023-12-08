package com.example.saller.di

import com.example.saller.utils.navigation.AppNavigationDispatcher
import com.example.saller.utils.navigation.AppNavigationHandler
import com.example.saller.utils.navigation.AppNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {


    @[Binds Singleton]
    fun bindAppNavDispatcher(impl:AppNavigationDispatcher):AppNavigator

    @[Binds Singleton]
    fun bindAppNavHandDispatcher(impl:AppNavigationDispatcher):AppNavigationHandler
}