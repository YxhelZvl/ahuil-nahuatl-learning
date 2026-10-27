package com.yxhelzvl.ahuil.core.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class PalabrasRepository() {
    private val db = Firebase.firestore

    suspend fun getPalabras(): List<String> {
        return try {
            val result = db.collection("Palabras").get().await()
            result.documents.map {
                it.getString("Nahuatl") ?: ""
            }
        } catch (e: Exception) {
            emptyList() // Or handle the exception as needed
        }
    }
    suspend fun getPalabrasEsp(): List<String> {
        return try {
            val result = db.collection("Palabras").get().await()
            result.documents.map {
                it.getString("Español") ?: ""
            }
        } catch (e: Exception) {
            emptyList() // Or handle the exception as needed
        }
    }
    suspend fun getPalabrasUid(): List<String> {
        return try {
            val result = db.collection("Palabras").get().await()
            result.documents.map {
                it.getString("Email") ?: ""
            }
        } catch (e: Exception) {
            emptyList() // Or handle the exception as needed
        }
    }
}