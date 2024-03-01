package com.example.fetchapp.repository

import android.util.Log
import com.example.fetchapp.model.Item
import com.example.fetchapp.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemsRepositoryImplementation : ItemsRepository {
    override suspend fun fetchItems(): List<Item>? = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.apiService.fetchItems()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("ItemsRepository", "Exception when fetching items: ${e.message}")
            null
        }
    }
}
