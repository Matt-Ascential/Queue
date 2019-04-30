package com.example.queue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class Line extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        final LinearLayout lm = findViewById(R.id.lineLay);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        Task<QuerySnapshot> orders = DataBase.getOrders();
        for (final QueryDocumentSnapshot ref : orders.getResult()) {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            TextView order = new TextView(this);
            order.setWidth(500);
            order.setText(ref.getString("order"));
            ll.addView(order);
            final Button btn = new Button(this);
            if (DataBase.isAdmin()) {
                btn.setText("Finish Order");
                btn.setLayoutParams(params);
                btn.setOnClickListener(new OnClickListener() {
                    QueryDocumentSnapshot snap = ref;
                    public void onClick(View v) {
                        DataBase.delete(snap.getId());
                        startActivity(new Intent(Line.this, Line.class));
                    }
                });
                ll.addView(btn);
            }
            lm.addView(ll);
        }
        DataBase.toggleRTD(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    return;
                }
                if (snapshots != null && snapshots.getDocuments().size() != DataBase.getOrderSize()) {
                    startActivity(new Intent(Line.this, Line.class));
                }
            }
        });
    }
}
