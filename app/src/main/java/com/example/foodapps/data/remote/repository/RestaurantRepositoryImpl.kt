package com.example.foodapps.data.remote.repository

import android.util.Log
import com.example.foodapps.data.remote.model.CategoryItem
import com.example.foodapps.data.remote.model.RestaurantFireBase
import com.example.foodapps.domain.repository.RestaurantRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): RestaurantRepository {
    companion object {
        private const val TAG = "RestaurantRepo"
        private const val RESTAURANTS_COLLECTION = "restaurants_list"
    }
    // Assuming you have a collection named "restaurants" in Firestore
    override fun getRestaurants(): Flow<List<RestaurantFireBase>> {
        return firestore.collection(RESTAURANTS_COLLECTION)
            .get().asFlow()
            .map { querySnapshot ->
                val categoryList = mutableListOf<RestaurantFireBase>()

                if (querySnapshot.documents.isNotEmpty()) {
                    Log.d(TAG, "ResturantsDetailsImageUrl::::::: ${querySnapshot.documents[0].getString("address")}")
                }
                for (document in querySnapshot.documents) {
                    val address = document.getString("address")
                    val is_available = document.getBoolean("is_available")
                    val rating = document.getString("rating")
                    val image = document.getString("image_url")
                    val name = document.getString("name")

                    val restaurantList = RestaurantFireBase(
                        id = document.id.takeIf { it.isNotEmpty() } ?: continue,
                        name = name ?: "Unknown Name",
                        address = address ?: "Unknown Address",
                        is_available = is_available ?: false,
                        rating = rating ?: "0.0",
                        image_url = image ?: "Unknown Image",
                    )
                    categoryList.add(restaurantList)
                }

                categoryList
            }
            .catch { exception ->
                Log.e("FirestoreRepo", "Error fetching categories: ", exception)
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