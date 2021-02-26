package com.shariful.generalknowledge;

import android.arch.persistence.room.Database;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shariful.generalknowledge.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("employee");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profile.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    String mobile = dataSnapshot1.child("mobile").getValue(String.class);
                    String name = dataSnapshot1.child("name").getValue(String.class);
                    String score = dataSnapshot1.child("score").getValue(String.class);

                    int iScore=Integer.parseInt(score);

                    String tempScore = dataSnapshot1.child("tempScore").getValue(String.class);
                    String view = dataSnapshot1.child("view").getValue(String.class);


                   // User2 p = dataSnapshot1.getValue(User2.class);

                    User2 user2 =new User2(mobile,name,iScore,tempScore,view);
                    profile.add(user2);

                }


                Collections.sort(profile, new Comparator<User2>() {
                    @Override
                    public int compare(User2 o1, User2 o2) {

                        return o2.getScore()-o1.getScore();
                    }
                });



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
                intent.putExtra("value","2");
                startActivity(intent);
            }
        });

    }
}
