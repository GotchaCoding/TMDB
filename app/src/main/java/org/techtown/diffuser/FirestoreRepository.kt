package org.techtown.diffuser

import com.google.firebase.firestore.FirebaseFirestore

interface FirestoreRepository {
    fun getFireStore() : FirebaseFirestore
}