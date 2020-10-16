package com.example.gateway

import com.example.domain.gateways.ChatGateway
import com.example.domain.messages.Message
import com.example.gateway.constants.EMPTY_FIREBASE_COLLECTION_MESSAGE
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.reactivex.Single

class ChatFirebase(
    private val firebaseUser: FirebaseUser?,
    private val firestore: FirebaseFirestore
) : ChatGateway {

    private lateinit var lastVisibleDocument: DocumentSnapshot

    override fun getMessages(
        page: Int,
        limit: Int
    ): Single<List<Message>?> {
        return Single.create { emitter ->
            firestore.collection("Dialog")
                .whereArrayContains("users", firebaseUser?.uid ?: "")
                .orderBy("lastMessage.time", Query.Direction.DESCENDING)
                .startAfter(lastVisibleDocument)
                .limit(limit.toLong() * page)
                .addSnapshotListener { value, error ->

                    if (error != null) {
                        emitter.onError(error)
                        return@addSnapshotListener
                    }

                    if (value == null || value.isEmpty) {
                        emitter.onError(Throwable(EMPTY_FIREBASE_COLLECTION_MESSAGE))
                        return@addSnapshotListener
                    }

                    val messages = ArrayList<Message>()

                    for (doc in value) {
                        try {
                            val currentMessage = Message(
                                doc.get("receiverUsername") as String,
                                doc.get("senderUsername") as String,
                                doc.get("text") as String,
                                (doc.get("time") as Timestamp).toDate()
                            )
                            messages.add(currentMessage)

                        } catch (e: Exception) {

                            emitter.onError(e)
                            return@addSnapshotListener
                        }
                    }
                    if (value.size() * page - 1 >= value.size()) {
                        lastVisibleDocument = value.documents[value.size() * page - 1]
                    }
                    emitter.onSuccess(messages)
                }

        }
    }

    override fun setPaginationInfo(dialogUid: String): Single<Int?> {
        return Single.create { emitter ->
            firestore.document(dialogUid)
                .collection("Message")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    lastVisibleDocument = it.documents[0]
                    emitter.onSuccess(it.size())
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }
}