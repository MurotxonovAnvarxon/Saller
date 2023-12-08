package com.example.saller.data.local.repository

import android.util.Log
import com.example.saller.data.local.model.ProductsData
import com.example.saller.data.local.model.toCommonData
import com.example.saller.data.local.myPref.MyPref
import com.example.saller.domain.AppRepository
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val pref: MyPref,
) : AppRepository {

    private val realtTimeFireBase = Firebase.database.getReference("Products")
    override fun login(name: String, password: String): Flow<Result<Unit>> = callbackFlow {
        fireStore.collection("Sellers")
            .whereEqualTo("sellerName", name)
            .get()
            .addOnSuccessListener {
                if (it.documents.isEmpty()) {
                    trySend(Result.failure(Exception("There is not such user")))
                } else {
                    it.documents.forEach {
                        if (it.data?.getOrDefault("password", "").toString()
                            == password
                        ) {
                            pref.saveId(it.id)
                            trySend(Result.success(Unit))
                        } else {
                            trySend(Result.failure(Exception("Password does not match")))
                        }
                    }
                }
            }
        awaitClose()
    }

    override fun deleteProduct(productsData: ProductsData): Flow<Result<String>> = callbackFlow {
        realtTimeFireBase.child(productsData.productName)
            .removeValue().addOnSuccessListener {
                Log.d("TAG", "deleteProduct")
            }
        awaitClose()
    }

    override fun getAllProducts(): Flow<List<ProductsData>> = callbackFlow {
        realtTimeFireBase
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(snapshot.children.map { it.toCommonData() })
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        awaitClose()
    }


    override fun editProducts(productsData: ProductsData): Flow<String> = callbackFlow {
        val updates = hashMapOf<String, Any?>(
            "productID" to productsData.productID,
            "productName" to productsData.productName,
            "productCount" to productsData.productCount,
            "productInitialPrice" to productsData.productInitialPrice,
            "productSellingPrice" to productsData.productSellingPrice,
            "productIsValid" to false,
            "productComment" to productsData.productComment,
        )

        realtTimeFireBase.child(productsData.productID).updateChildren(updates)
            .addOnSuccessListener {
                trySend("ishladi")
            }
            .addOnFailureListener {
                trySend(it.toString())
            }

        awaitClose()
    }
}

