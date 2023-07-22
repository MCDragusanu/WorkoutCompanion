package com.example.workoutcompanion.core.data.firestore

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow

abstract class FirebaseRepository<T:Any>( private val collectionReference: CollectionReference) {


   abstract suspend fun createDocument(documentName:String , content:T )

   abstract suspend fun updateDocument(documentName: String , content: Map<String , Any>)

   abstract suspend fun deleteDocument(documentName: String)

   abstract suspend fun getDocument(documentName: String , classType : Class<T> ,):Flow<T?>

   abstract suspend fun getAllDocuments(classType: Class<T>):Flow<T>

   fun getCollection() =this.collectionReference
}