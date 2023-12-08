package com.example.saller.domain

import com.example.saller.data.local.model.ProductsData
import kotlinx.coroutines.flow.Flow

interface AppRepository {



    fun login(name: String, password: String): Flow<Result<Unit>>


    //products

    fun deleteProduct(productsData: ProductsData): Flow<Result<String>>

    fun getAllProducts(): Flow<List<ProductsData>>

    fun editProducts(
        productsData: ProductsData
    ): Flow<String>


}