package com.example.workoutcompanion.core.data.firestore

class CollectionEmpty(val collectionName:String):Exception("Can't retrieve collection $collectionName. It is empty or not found") {
}