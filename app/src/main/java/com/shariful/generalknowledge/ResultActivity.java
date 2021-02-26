package com.shariful.generalknowledge;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {
    TextView qsn,pointsTV;
    Button playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        qsn=findViewById(R.id.totalQsnID);
        pointsTV=findViewById(R.id.totalPointsID);
        playAgain=findViewById(R.id.playAgainBtnID);

        final String value=getIntent().getStringExtra("points");

       // int i=Integer.parseInt(value);
        //Toast.makeText(this, "point is: "+value, Toast.LENGTH_SHORT).show();
        pointsTV.setText("Score: "+value);

      //  String id= Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);



        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ResultActivity.this,MainActivity.class);
                intent.putExtra("value","2");
                startActivity(intent);
            }
        });


    }
}
