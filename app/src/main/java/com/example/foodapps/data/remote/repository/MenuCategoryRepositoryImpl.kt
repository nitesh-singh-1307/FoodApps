package com.example.foodapps.data.remote.repository

import android.util.Log
import com.example.foodapps.data.remote.model.CategoryItem
import com.example.foodapps.domain.repository.MenuCategoryRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class MenuCategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : MenuCategoryRepository {
    override fun getMenuCategories(): Flow<List<CategoryItem>> {
        return firestore.collection("Menu_category")
            .get().asFlow()
            .map { querySnapshot ->
                val categoryList = mutableListOf<CategoryItem>()
                for (document in querySnapshot.documents) {
                    val name = document.getString("category_name")
                    val image = document.getString("category_image")
                    if (name != null && image != null) {
                        val category = CategoryItem(
                            id = document.id.takeIf { it.isNotEmpty() } ?: continue,
                            name = name,
                            image = image
                        )
                        categoryList.add(category)
                    }
                }
                categoryList
            }
            .catch { exception ->
                Log.e("FirestoreRepo", "Error fetching categories: ", exception)
//                emit(emptyList())
            }.flowOn(Dispatchers.IO)
    }

    inline fun <reified T> DocumentSnapshot.toObject(): T? {
        return this.toObject(T::class.java)
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