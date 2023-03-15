package com.example.sampleproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.sampleproject.adapter.ProductAdapter
import com.example.sampleproject.databinding.ActivityMainBinding
import com.example.sampleproject.db.FakerDB
import com.example.sampleproject.models.Products
import com.example.sampleproject.reposistory.ProductRepository
import com.example.sampleproject.retrofit.FakerApi
import com.example.sampleproject.utils.NetworkResult
import com.example.sampleproject.viewmodel.MainViewModel
import com.example.sampleproject.viewmodel.MainViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductAdapter
    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        (application as FakerApplication).applicationCompoent.inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        adapter = ProductAdapter()

        binding.productList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.productList.adapter = adapter

        setUpDatabaseObserver()
        setContentView(binding.root)

    }

    private fun setUpDatabaseObserver() {

        mainViewModel.getAllProducts()
            .observe(this, Observer { it ->
                if (it.isNotEmpty()) {

                    binding.progressBar.isVisible = false
                    adapter.submitList(it)


                } else {
                    setUpApiCallObserver()
                }
            })
    }

    private fun setUpApiCallObserver() {
        mainViewModel.productsLiveData.observe(this, Observer {

            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.isVisible = false

                    if (it.data?.isNotEmpty() == true) {
                        adapter.submitList(it.data)
                        adapter.notifyDataSetChanged()
                        insertDataToDb(products = it.data)

                    }
                }
                is NetworkResult.Error -> Toast.makeText(
                    this,
                    it.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
                is NetworkResult.Loading -> binding.progressBar.isVisible = true
            }
        })

        mainViewModel.getProducts()
    }

    private fun insertDataToDb(products: List<Products>) {
        mainViewModel.insertProducts(products)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}