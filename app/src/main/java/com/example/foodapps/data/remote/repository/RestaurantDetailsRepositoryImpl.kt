package com.example.foodapps.data.remote.repository

import android.util.Log
import com.example.foodapps.data.remote.model.RestaurantDetails
import com.example.foodapps.data.remote.model.RestaurantLikeDetails
import com.example.foodapps.domain.repository.RestaurantDetailsRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestaurantDetailsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth // Needed to get current user ID easily
) : RestaurantDetailsRepository {
    companion object {
        private const val TAG = "MenuItemRepo"
        private const val RESTAURANTS_COLLECTION = "restaurants_list"
        private const val MENU_ITEMS_SUBCOLLECTION = "menuItems" // Name of your subcollection
        private const val USERS_COLLECTION = "users"
        private const val FAVORITE_MENU_ITEMS_FIELD = "favoriteMenuItemIds"
    }

    private fun getCurrentUserId(): String? = firebaseAuth.currentUser?.uid


    override fun getRestaurantDetails(restaurantId: String): Flow<RestaurantDetails> {
        Log.d(TAG, "Fetching menu items for restaurant ID: $restaurantId")
        // Path to the 'menu' subcollection
        return firestore.collection(RESTAURANTS_COLLECTION)
            .document(restaurantId)
            .collection(MENU_ITEMS_SUBCOLLECTION)
            .limit(1)
            .snapshots()
            .map { snapshot ->
                Log.d(TAG, "Fetching menu Snapshot: $snapshot")
                if (snapshot.isEmpty) {
                    Log.d(TAG, "No menu document found for restaurant $restaurantId")
                    // Return a default or empty RestaurantDetails, or throw/cancel if not found
                    throw NoSuchElementException("No menu document found for restaurant $restaurantId")
                } else {
                    val document = snapshot.documents.first()
                    val item_name = document.getString("item_name")
                    val category = document.getString("category")
                    val price = document.getString("price")
                    val address = document.getString("address")
                    val time = document.getString("time")
                    val title = document.getString("title")
                    val description = document.getString("description")
                    val cuisine_type = document.getString("cuisine_type")
                    val is_available = document.getBoolean("is_available")
                    val is_like = document.getBoolean("is_like")
                    val item_image_url = document.getString("item_image_url")

                    if (item_name != null && category != null
                        && price != null && description != null
                        && item_image_url != null && is_available != null && cuisine_type != null
                    ) {
                        RestaurantDetails(
                            id = document.id,
                            item_name = item_name,
                            description = description,
                            price = price,
                            address = address ?: "",
                            time = time ?: "",
                            title = title ?: "",
                            category = category,
                            cuisine_type = cuisine_type,
                            item_image_url = item_image_url,
                            is_available = is_available,
                            is_like = is_like ?: false,
                        )
                    } else {
                        throw IllegalStateException("Missing fields in menu document for restaurant $restaurantId")
                    }
                }
            }.catch { exception ->
                Log.e(TAG, "Error fetching menu document flow for $restaurantId", exception)
                throw exception
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateRestaurantDetailsLike(
        restaurantId: String,
        menuItemId: String,
        newLikeStatus: Boolean
    ): Flow<RestaurantDetails> = flow {
        Log.d(TAG, "Updating is_like for restaurant ID: $restaurantId, menuItemId: $menuItemId , newLikeStatus: $newLikeStatus" )

        if (restaurantId.isBlank() || menuItemId.isBlank()) {
            throw IllegalArgumentException("Restaurant ID and Menu Item ID must not be blank")
        }
        // Update the is_like field
        firestore.collection(RESTAURANTS_COLLECTION)
            .document(restaurantId)
            .collection(MENU_ITEMS_SUBCOLLECTION)
            .document(menuItemId)
            .update("is_like", newLikeStatus)
            .await()

        // Fetch the updated document
        val document = firestore.collection(RESTAURANTS_COLLECTION)
            .document(restaurantId)
            .collection(MENU_ITEMS_SUBCOLLECTION)
            .document(menuItemId)
            .get()
            .await()

        if (document.exists()) {
            val item_name = document.getString("item_name")
            val category = document.getString("category")
            val price = document.getString("price")
            val address = document.getString("address")
            val time = document.getString("time")
            val title = document.getString("title")
            val description = document.getString("description")
            val cuisine_type = document.getString("cuisine_type")
            val is_available = document.getBoolean("is_available")
            val is_like = document.getBoolean("is_like")
            val item_image_url = document.getString("item_image_url")

            if (item_name != null && category != null
                && price != null && description != null
                && item_image_url != null && is_available != null && cuisine_type != null
            ) {
                emit(
                    RestaurantDetails(
                        id = document.id,
                        item_name = item_name,
                        description = description,
                        price = price,
                        address = address ?: "",
                        time = time ?: "",
                        title = title ?: "",
                        category = category,
                        cuisine_type = cuisine_type,
                        item_image_url = item_image_url,
                        is_available = is_available,
                        is_like = is_like ?: false,
                    )
                )
            } else {
                throw IllegalStateException("Missing fields in menu document for restaurant $restaurantId")
            }
        } else {
            throw NoSuchElementException("No menu document found for restaurant $restaurantId")
        }
    }.catch { exception ->
        Log.e(TAG, "Error updating is_like for $restaurantId", exception)
        throw exception
    }.flowOn(Dispatchers.IO)

    override suspend fun addFavorite(
        userId: String,
        menuItemId: String,
        initialQuantity: Int
    ){
        Log.d(TAG, "Adding $menuItemId to favorites for user $userId")
        if (userId.isBlank()) {
            throw IllegalArgumentException("User ID is blank")
        }
        try {
            val favoriteItemData = hashMapOf(
                "menuItemId" to menuItemId, // Store the ID of the menu item
                "quantity" to initialQuantity,
                "addedAt" to System.currentTimeMillis() // Optional: timestamp
            )

            firestore.collection(USERS_COLLECTION).document(userId)
                .collection(FAVORITE_MENU_ITEMS_FIELD).document(menuItemId)
                .set(favoriteItemData)
                .await()
            Log.d(TAG, "Added $menuItemId to favorites for user $userId")
        } catch (e: Exception) {
            Log.e(TAG, "Error adding favorite for $userId: ${e.message}", e)
            throw e
        }
    }


    override suspend fun removeFavorite(
        userId: String,
        menuItemId: String
    ){
    }


}