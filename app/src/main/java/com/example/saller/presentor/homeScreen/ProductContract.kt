package com.example.saller.presentor.homeScreen

import com.example.saller.data.local.model.ProductsData
import kotlinx.coroutines.flow.StateFlow

interface ProductContract {

    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatchers(intent: Intent)

    }

    interface Intent {
        object MoveToSearchScreen : Intent
        object MoveToStatisticScreen : Intent
        data class DeleteProduct(val productsData: ProductsData) : Intent

        data class EditProduct(
            val productID: String,
            val productName: String,
            val productCount: Int,
            val productInitialPrice: Int,
            val productSellingPrice: Int,
            val productIsValid: Boolean,
            val productComment: String
        ): Intent

    }

    data class UIState(
        val productList: List<ProductsData> = emptyList(),
        val isLoading: Boolean = false
    )


}