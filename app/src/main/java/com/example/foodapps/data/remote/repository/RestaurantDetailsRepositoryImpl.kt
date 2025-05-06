package com.example.foodapps.data.remote.repository

import android.util.Log
import com.example.foodapps.data.remote.model.RestaurantDetails
import com.example.foodapps.domain.repository.RestaurantDetailsRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RestaurantDetailsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RestaurantDetailsRepository {
    companion object {
        private const val TAG = "MenuItemRepo"
        private const val RESTAURANTS_COLLECTION = "restaurants"
        private const val MENU_ITEMS_SUBCOLLECTION = "menuItems" // Name of your subcollection
    }

    override fun getRestaurantDetails(restaurantId: String): Flow<List<RestaurantDetails>> {
        Log.d(TAG, "Fetching menu items for restaurant ID: $restaurantId")
        return firestore.collection(RESTAURANTS_COLLECTION)
            .document(restaurantId).collection(MENU_ITEMS_SUBCOLLECTION).snapshots().get().asFlow()
            .map { snapshot ->
                val menuItems = mutableListOf<RestaurantDetails>()
                for (document in snapshot.documents) {
                    val item_name = document.getString("item_name")
                    val category = document.getString("category")
                    val price = document.getString("price")
                    val description = document.getString("description")
                    val cuisine_type = document.getString("cuisine_type")
                    val is_available = document.getBoolean("is_available")
                    val item_image_url = document.getString("item_image_url")

                    if (item_name != null && category != null
                        && price != null && description != null
                        && item_image_url != null &&
                        is_available != null && cuisine_type != null
                    ) {
                        val menuItem = RestaurantDetails(
                            id = document.id,
                            item_name = item_name,
                            description = description,
                            price = price,
                            category = category,
                            cuisine_type = cuisine_type,
                            item_image_url = item_image_url,
                            is_available = is_available
                        )

                        menuItems.add(menuItem)
                    }
                }
                menuItems
            }.catch { exception ->
                Log.e("FirestoreRepo", "Error fetching categories: ", exception)
//                emit(emptyList())
            }.flowOn(Dispatchers.IO)
    }

    private fun Task<QuerySnapshot>.asFlow(): Flow<QuerySnapshot> =
        flow {
            try {
                val querySnapshot = await()
                emit(querySnapshot)
            } catch (e: Exception) {
                Log.e("FirestoreRepo", "Error getting documents: ", e)
                throw e
            }

        }


}