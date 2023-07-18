package com.example.workoutcompanion.firestore

class CollectionEmpty(val collectionName:String):Exception("Can't retrieve collection $collectionName. It is empty or not found") {
}