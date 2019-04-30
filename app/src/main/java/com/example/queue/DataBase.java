package com.example.queue;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;


public class DataBase {
    public static void logIn(String input) {
        logIn(input.hashCode());
    }
    private static void logIn(final long input) {
        if (db == null) {
            throw new IllegalStateException();
        }
        Task<DocumentSnapshot> task = db.document("password/password").get();
        while (!task.isComplete()) {}
        admin = input == task.getResult().getLong("password");
    }
    public static boolean isAdmin() {
        return admin;
    }
    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        DataBase.name = name;
    }
    public static Task<QuerySnapshot> getOrders() {
         Task<QuerySnapshot> task = db.collection("order").get();
         while(!task.isComplete()) {}
         orderSize = task.getResult().size();
         return task;
    }
    public static void delete(String id) {
        db.collection("order").document(id).delete();
    }
    public static void toggleRTD(EventListener<QuerySnapshot> event) {
        Query query = db.collection("order");
        if (event == null) {
            System.out.println("this is test");
            ListenerRegistration registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                  @Override
                  public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) { }
            });
            registration.remove();
            return;
        }
        query.addSnapshotListener(event);
    }
    public static void pushOrder(String Order) {

    }
    public static int getOrderSize() {
        return orderSize;
    }
    private static int orderSize = 0;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static boolean admin = false;
    private static String name;
}
