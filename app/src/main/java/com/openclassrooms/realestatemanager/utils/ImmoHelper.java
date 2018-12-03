package com.openclassrooms.realestatemanager.utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ImmoHelper {

    private static final String COLLECTION_NAME = "immovables";

    // --- COLLECTION REFERENCE ---
    public static CollectionReference getImmosCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---
    /*
    public static Task<Void> createImmo() {
        Immo immoToCreate = new Immo();
        return ImmoHelper.getImmosCollection().document(id).set(immoToCreate);

    }
    */

    // --- GET ---
    public static Task<DocumentSnapshot> getImmoById(String id){
        return ImmoHelper.getImmosCollection().document(id).get();
    }

    public static Task<QuerySnapshot> getAllImmos(){
        return ImmoHelper.getImmosCollection().get();
    }

    // --- UPDATE ---
    public static Task<Void> updateImmoString(String field, String value, String id) {
        return ImmoHelper.getImmosCollection().document(id).update(field, value);
    }

    public static Task<Void> updateImmoInt(String field, int value, String id) {
        return ImmoHelper.getImmosCollection().document(id).update(field, value);
    }

    // --- DELETE ---
    public static Task<Void> deleteImmo(String id) {
        return ImmoHelper.getImmosCollection().document(id).delete();
    }

}
