package com.example.fetchapp.repository


import com.example.fetchapp.model.Item


interface ItemsRepository {
    suspend fun fetchItems(): List<Item>?
}
