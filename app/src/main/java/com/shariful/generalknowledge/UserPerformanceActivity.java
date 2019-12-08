package com.shariful.generalknowledge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shariful.generalknowledge.adapter.CustomAdapter;

import java.util.ArrayList;

public class UserPerformanceActivity extends AppCompatActivity {
Button homeBtn;

    RecyclerView recyclerView;
    ArrayList<User2> profile;
    CustomAdapter adapter;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_performance);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewID);
        homeBtn=findViewById(R.id.homeBtnID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        profile = new ArrayList<User2>();
        adapter = new CustomAdapter(UserPerformanceActivity.this,profile);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("employee");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profile.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    User2 p = dataSnapshot1.getValue(User2.class);
                    profile.add(p);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserPerformanceActivity.this, "Oops something is wrong!", Toast.LENGTH_SHORT).show();

            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPerformanceActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
