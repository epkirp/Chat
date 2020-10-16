package com.example.gateway

import com.example.domain.gateways.ChatsGateway
import com.example.domain.messages.Dialog
import com.example.domain.messages.Message
import com.example.gateway.constants.EMPTY_FIREBASE_COLLECTION_MESSAGE
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

class ChatsFirebase(
    private val firebaseUser: FirebaseUser?,
    private val firestore: FirebaseFirestore
) : ChatsGateway {

    private lateinit var lastVisibleDocument: DocumentSnapshot

    override fun getChats(
        page: Int,
        limit: Int
    ): Single<List<Dialog>?> {
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

                    val dialogs = ArrayList<Dialog>()

                    for (doc in value) {
                        try {
                            val lastMessageHashMap =
                                (doc.get("lastMessage") as HashMap<*, *>)

                            val lastMessage = Message(
                                lastMessageHashMap["receiverUsername"] as String,
                                lastMessageHashMap["senderUsername"] as String,
                                lastMessageHashMap["text"] as String,
                                ((lastMessageHashMap["time"]) as Timestamp).toDate()
                            )

                            val users = ArrayList<String>(doc.get("users") as List<String>)

                            dialogs.add(
                                Dialog(
                                    doc.id,
                                    lastMessage,
                                    users
                                )
                            )
                        } catch (e: Exception) {
                            emitter.onError(e)
                            return@addSnapshotListener
                        }
                    }
                    if (value.size() * page - 1 >= value.size()) {
                        lastVisibleDocument = value.documents[value.size() * page - 1]
                    }
                    emitter.onSuccess(dialogs)
                }

        }
    }

    override fun setPaginationInfo(): Single<Int?> {
        return Single.create { emitter ->
            firestore.collection("dialog")
                .whereArrayContains("users", firebaseUser?.uid ?: "")
                .orderBy("lastMessage.time", Query.Direction.DESCENDING)
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