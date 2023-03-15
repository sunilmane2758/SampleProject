package com.example.sampleproject.reposistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sampleproject.db.FakerDB
import com.example.sampleproject.models.Products

import com.example.sampleproject.retrofit.FakerApi
import com.example.sampleproject.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val fakerApi: FakerApi,
    private val fakerDB: FakerDB
) {


    private val _productsLiveData = MutableLiveData<NetworkResult<List<Products>>>()

    val productsLiveData: LiveData<NetworkResult<List<Products>>>
        get() = _productsLiveData


    suspend fun getProducts() {

        _productsLiveData.postValue(NetworkResult.Loading())

        val response = fakerApi.getProducts()

        if (response.isSuccessful && response.body() != null) {
            _productsLiveData.postValue(NetworkResult.Success(response.body()!!))
            response.body()?.forEach {
                insertProducts(response.body()!!)
            }

        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _productsLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _productsLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    fun getAllProducts(): LiveData<List<Products>> {
        return fakerDB.getFakerDAO().getProducts()
    }

    suspend fun insertProducts(products: List<Products>) {
        fakerDB.getFakerDAO().addProducts(products)
    }
}