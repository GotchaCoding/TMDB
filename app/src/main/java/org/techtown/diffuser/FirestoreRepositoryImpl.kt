package org.techtown.diffuser

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor() : FirestoreRepository {

    override fun getFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }
}