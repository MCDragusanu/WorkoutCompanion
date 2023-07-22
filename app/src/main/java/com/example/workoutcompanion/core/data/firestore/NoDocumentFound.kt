package com.example.workoutcompanion.core.data.firestore

class NoDocumentFound(val documentId:String):Exception("No document was found with the id $documentId")