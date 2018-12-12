package com.openclassrooms.realestatemanager.utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;

public class FirebaseImmoHelper {

    private static final String COLLECTION_NAME = "immovables";

    // --- COLLECTION REFERENCE ---
    public static CollectionReference getImmosCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---
    public static Task<Void> createImmo(Immo immoToCreate) {
        return FirebaseImmoHelper.getImmosCollection().document(String.valueOf(immoToCreate.getId())).set(immoToCreate);
    }

    // --- GET ---
    public static Task<DocumentSnapshot> getImmoById(String id){
        return FirebaseImmoHelper.getImmosCollection().document(id).get();
    }

    public static Task<QuerySnapshot> getAllImmos(){
        return FirebaseImmoHelper.getImmosCollection().get();
    }

    // --- UPDATE ---
    public static Task<Void> updateImmo(Immo immoToUpdate) {
        return FirebaseImmoHelper.getImmosCollection().document(String.valueOf(immoToUpdate.getId())).set(immoToUpdate);
    }

    // --- DELETE ---
    public static Task<Void> deleteImmo(String id) {
        return FirebaseImmoHelper.getImmosCollection().document(id).delete();
    }

}
