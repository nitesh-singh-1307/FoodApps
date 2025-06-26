package com.example.foodapps.data.remote.repository

import android.util.Log
import com.example.foodapps.data.remote.model.RestaurantDetails
import com.example.foodapps.data.remote.model.RestaurantFireBase
import com.example.foodapps.data.remote.model.RestaurantLikeDetails
import com.example.foodapps.domain.repository.LikedItemsRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LikedItemsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : LikedItemsRepository {

    companion object {
        private const val TAG = "LikedItemsRepo"
        private const val MENU_ITEMS_COLLECTION_GROUP_ID = "menuItems" // ID of the subcollection
    }

    override fun getLikedMenuItems(): Flow<List<RestaurantLikeDetails>> {
        Log.d(TAG, "Fetching all liked menu items using collection group query...")
        return firestore.collectionGroup(MENU_ITEMS_COLLECTION_GROUP_ID)
            .whereEqualTo("is_like", true) // Filter for liked items
            .snapshots()
            .map { snapshot ->
                val categoryList = mutableListOf<RestaurantLikeDetails>()
                Log.d(TAG, "Snapshot::::::: $snapshot")
                for (document in snapshot.documents) {
                    Log.d(TAG, "Document:::: ${document.getBoolean("is_like")}")
                    val item_name = document.getString("item_name")
                    val category = document.getString("category")
                    val price = document.getString("price")
                    val address = document.getString("address")
                    val time = document.getString("time")
                    val title = document.getString("title")
                    val description = document.getString("description")
                    val cuisine_type = document.getString("cuisine_type")
                    val delivery_type = document.getString("delivery_type")
                    val is_available = document.getBoolean("is_available") ?: false
                    val is_like = document.getBoolean("is_like") ?: false
                    val item_image_url = document.getString("item_image_url")

                    if (item_name != null && category != null && price != null && description != null
                        && item_image_url != null && cuisine_type != null) {
                        val restaurantDetails = RestaurantLikeDetails(
                            id = document.id,
                            item_name = item_name,
                            category = category,
                            price = price,
                            address = address ?: "Unknown Address",
                            time = time ?: "Unknown Time",
                            title = title ?: "Unknown Title",
                            description = description,
                            cuisine_type = cuisine_type,
                            delivery_type = delivery_type!!,
                            is_available = is_available,
                            is_like = is_like,
                            item_image_url = item_image_url
                        )
                        categoryList.add(restaurantDetails)
                    }
                }
                categoryList
            }.catch { exception ->
                Log.e("FirestoreRepo", "Error fetching is_like:::: ", exception)
//                emit(emptyList())
            }
            .flowOn(Dispatchers.IO)
    }
}