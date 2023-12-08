package com.example.saller.presentor.homeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.saller.R
import com.example.saller.data.local.model.ProductsData
import com.example.saller.utils.DeleteDialog
import com.example.saller.utils.EditProductsDialog
import com.example.saller.utils.ProductItem
import com.example.saller.utils.SimpleSearchItem

class ProductScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val vm: ProductContract.ViewModel = getViewModel<ProductViewModel>()
        ProductsScreenContent(
            uiState = vm.uiState.collectAsState(),
            onEventDispatcher = vm::onEventDispatchers
        )
    }
}


@Composable
fun ProductsScreenContent(
    uiState: State<ProductContract.UIState>,
    onEventDispatcher: (ProductContract.Intent) -> Unit
) {
    val productID = remember { mutableStateOf("") }
    val productName = remember { mutableStateOf("") }
    val productCount = remember { mutableStateOf(0) }
    val productInitialPrice = remember { mutableStateOf(0) }
    val productSellingPrice = remember { mutableStateOf(0) }
    val productIsValid = remember { mutableStateOf(false) }
    val productComment = remember { mutableStateOf("") }

    var searchText by remember { mutableStateOf("") }

    val showDialog = remember { mutableStateOf(false) }
    val showEditProductDialog = remember { mutableStateOf(false) }
//    if (showDialog.value) DeleteDialog(
//        onClickDelete = {
//            onEventDispatcher.invoke(
//                ProductContract.Intent.DeleteProduct(
//                    ProductsData(
//                        productID.value,
//                        productName.value,
//                        productCount.value,
//                        productInitialPrice.value,
//                        productSellingPrice.value,
//                        productIsValid.value,
//                        productComment.value
//                    )
//                )
//            )
//            showDialog.value = false
//
//
//        },
//        onClickCancel = { showDialog.value = false }
//    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (uiState.value.isLoading) {
            CircularProgressIndicator(
                color = Color(0xFF03A9F4),
                strokeWidth = 2.dp,
            )
        }

        SimpleSearchItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp),
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = "Search..."
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xFFFFC107))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Products List",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))

        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 140.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.value.productList.filter {
                it.productName.contains(searchText, ignoreCase = false)&&it.productIsValid==true
            }) {
                ProductItem(
                    model = it,
                    onClick = {
//                        onEventDispatcher.invoke(
//                            ProductContract.Intent.MoveToAddProductsScreen
//                        )
                    },
                    onClickDelete = { data ->
                        productName.value = data.productName
                        productID.value = data.productID
                        productCount.value = data.productCount
                        productInitialPrice.value = data.productInitialPrice
                        productSellingPrice.value = data.productSellingPrice
                        productIsValid.value = data.productIsValid
                        productComment.value = data.productComment
                        showDialog.value = true
                    },
                    onEdit = { data ->
                        productName.value = data.productName
                        productID.value = data.productID
                        productCount.value = data.productCount
                        productInitialPrice.value = data.productInitialPrice
                        productSellingPrice.value = data.productSellingPrice
                        productIsValid.value = data.productIsValid
                        productComment.value = data.productComment
                        showEditProductDialog.value = true
                    }
                )
            }
        }
//        Button(
//            onClick = {
//                onEventDispatcher(ProductContract.Intent.MoveToAddProductsScreen)
//            }, colors = ButtonDefaults.buttonColors(
//                containerColor = Color(0xFF3F51B5)
//            ), shape = RoundedCornerShape(100),
//            modifier = Modifier
//                .padding(20.dp)
//                .align(Alignment.BottomEnd)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier
//                    .size(40.dp),
//                tint = Color.White
//            )
//        }
    }
    if (showEditProductDialog.value) EditProductsDialog(
        ProductsData(
            productName.value,
            productID.value,
            productCount.value,
            productInitialPrice.value,
            productSellingPrice.value,
            productIsValid.value,
            productComment.value,
        ),
        onClickEdit = {
            onEventDispatcher.invoke(
                ProductContract.Intent.EditProduct(
                    productName.value,
                    productID.value,
                    productCount.value,
                    productInitialPrice.value,
                    productSellingPrice.value,
                    productIsValid.value,
                    productComment.value,
                )
            )
            showEditProductDialog.value = false

        },
        onClickCancel = { showEditProductDialog.value = false },
    )

}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun MainScreenPreview() {
    ProductsScreenContent(
        uiState = mutableStateOf(ProductContract.UIState()),
        onEventDispatcher = {})
}
