package com.shariful.generalknowledge;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NoticeActivity extends AppCompatActivity {
    EditText notice;
    Button publishBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        notice=findViewById(R.id.noticeETID);
        publishBtn=findViewById(R.id.publishBtnID);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("notice");

        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noticeText=notice.getText().toString();
                myRef.setValue(noticeText);
                notice.setText("");
            }
        });


    }
}
