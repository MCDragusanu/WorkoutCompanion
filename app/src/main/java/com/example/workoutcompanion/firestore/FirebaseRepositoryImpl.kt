package com.example.workoutcompanion.firestore

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImpl<T:Any>(collectionReference: CollectionReference) : FirebaseRepository<T>(collectionReference) {
    override suspend fun createDocument(documentName: String, content: T) {
        getCollection().document(documentName).set(content).addOnFailureListener {
            it.printStackTrace()
            throw it
        }.await()
    }

    override suspend fun updateDocument(documentName: String, content: Map<String, Any>) {
        getCollection().document(documentName).update(content).addOnFailureListener {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun deleteDocument(documentName: String) {
        getCollection().document(documentName).delete().addOnFailureListener {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun getDocument(documentName: String, classType: Class<T>): Flow<T?> =
        callbackFlow {
            val listener =
                EventListener<DocumentSnapshot> { snapshot, error ->
                    if (error != null) {
                        error.printStackTrace()
                        cancel(error.localizedMessage ?: "Unknown Error", error)
                    } else {
                        snapshot?.let {
                            if (it.exists()) {
                                //document found
                                val item = snapshot.toObject(classType)
                                trySend(item)
                                close()
                            } else {
                                //No document found ot is empty
                                val exception = NoDocumentFound(documentName)
                                cancel(exception.documentId, exception)
                            }
                        }
                    }
                }
            val registration = getCollection().document(documentName).addSnapshotListener(listener)
            awaitClose {
                registration.remove()
            }
        }

    override suspend fun getAllDocuments(classType: Class<T>): Flow<T> = callbackFlow {
        val listener = EventListener<QuerySnapshot> { snapshot, error ->
            if (error != null) {
                error.printStackTrace()
                cancel(error.localizedMessage ?: "Unknown Error", error)
            } else {
                snapshot?.let {
                    if (it.isEmpty) {
                        val exception = CollectionEmpty(getCollection().id)
                        cancel(exception.collectionName, exception)
                    } else {
                        it.documents.onEach { document ->
                            val item = document.toObject(classType)
                            if (item == null) {
                                println("Failed to convert to POJO document ${document.id}")
                            } else trySend(item)
                        }
                        close()
                    }
                }
            }
        }
        val registration = getCollection().addSnapshotListener(listener)
        awaitClose {
            registration.remove()
        }
    }
}