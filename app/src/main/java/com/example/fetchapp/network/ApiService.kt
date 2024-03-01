package com.example.fetchapp.network
import com.example.fetchapp.model.Item
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun fetchItems(): Response<List<Item>>
}
