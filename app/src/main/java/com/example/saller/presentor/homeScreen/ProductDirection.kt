package com.example.saller.presentor.homeScreen

import com.example.saller.presentor.statisticScreen.StatisticScreen
import com.example.saller.utils.navigation.AppNavigator
import javax.inject.Inject

interface ProductDirection {
    suspend fun moveToSearchScreen()
    suspend fun moveToStatisticScreen()
}


class ProductDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : ProductDirection {
    override suspend fun moveToSearchScreen() {
//        appNavigator.addScreen(SearchScreen())
    }

    override suspend fun moveToStatisticScreen() {
        appNavigator.addScreen(StatisticScreen())
    }


}