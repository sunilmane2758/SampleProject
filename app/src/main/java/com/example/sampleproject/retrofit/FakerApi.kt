package com.example.sampleproject.retrofit

import com.example.sampleproject.models.Products

import retrofit2.Response
import retrofit2.http.GET

interface FakerApi {

    @GET("products")
    suspend fun getProducts(): Response<List<Products>>
}