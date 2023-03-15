package com.example.sampleproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleproject.models.Products
import com.example.sampleproject.reposistory.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository) : ViewModel() {

    val productsLiveData get() = repository.productsLiveData

    fun getProducts() {
        viewModelScope.launch() {
            repository.getProducts()
        }
    }

    fun getAllProducts(): LiveData<List<Products>> {
        return repository.getAllProducts()
    }

    fun insertProducts(products: List<Products>) {
        viewModelScope.launch() {
            repository.insertProducts(products)
        }

    }
}

