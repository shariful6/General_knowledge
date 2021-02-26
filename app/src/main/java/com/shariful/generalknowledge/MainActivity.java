package com.shariful.generalknowledge;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button startPlayingBtn;
    TextView scorTV,breakTV;

    private AlertDialog.Builder builder_notice;


    private FirebaseAuth mAuth;
    DatabaseReference databaseReference,database;
    String uId;
    String value;
    int date;
    private FirebaseUser user;
    String noticeText;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value=getIntent().getStringExtra("value");

        databaseReference= FirebaseDatabase.getInstance().getReference("employee");
        database= FirebaseDatabase.getInstance().getReference("notice");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startPlayingBtn=findViewById(R.id.startBtnID);
        scorTV=findViewById(R.id.scoreTVID);
        breakTV=findViewById(R.id.breakTVID);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uId = user.getUid();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

         status();
       //  progressDialogOpen();
         retriveScore();
         retriveNotice();


        startPlayingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,QuizActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {

            openDialog_notice();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.messageID) {
            sendMail();
            return true;
        }
        if (id == R.id.rulseID) {

            Intent intent = new Intent(MainActivity.this,RulseActivity.class);
            startActivity(intent);
            return true;
        }

        else if (id == R.id.shareID) {
            Intent myIntent=new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body="http://play.google.com/store/apps/details?id=com.shariful.generalknowledge";
            myIntent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(myIntent,"Share Using"));
            return true;

        } else if (id == R.id.rateID) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + getPackageName())));
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));

            }

            return true;
        }
        else if (id == R.id.policyID) {

            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.adminID) {

            openDialog();

        }
        else if (id == R.id.userID) {
            Intent intent =new Intent(MainActivity.this,UserPerformanceActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendMail() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT,"General Knowledge App Feedback");
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an email client"));
    }

    public void retriveScore()
    {
        databaseReference.child(uId).child("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String rScore = dataSnapshot.getValue(String.class);
                    scorTV.setText("Total Score: "+rScore);


                }
                else{

                    scorTV.setText("Total Score: "+"0");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openDialog()
    {
        AdminDialog adminDialog=new AdminDialog();
        adminDialog.show(getSupportFragmentManager(),"Admin Login");

    }
    public void openDialog_notice()
    {

        builder_notice =new AlertDialog.Builder(MainActivity.this);

        builder_notice.setTitle("Notice");
        builder_notice.setMessage(noticeText);
        builder_notice.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog=builder_notice.create();
        alertDialog.show();

    }
    public  void retriveNotice()
    {

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                 noticeText = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void status()
    {
        int i=Integer.parseInt(value);
        if (i==1)
        {
            breakTV.setVisibility(View.VISIBLE);
            startPlayingBtn.setEnabled(false);

            Calendar calendar= Calendar.getInstance();
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd");//pattern: dd-mmm-yyyy
            String   current_date = simpleDateFormat.format(calendar.getTime());
            databaseReference.child(uId).child("date").setValue(current_date);
        }

        if (i==2)
        {
         retriveDate();
        }

    }

    private void retriveDate()
    {
        databaseReference.child(uId).child("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String rDate = dataSnapshot.getValue(String.class);
                    int idate=Integer.parseInt(rDate);

                    //Toast.makeText(MainActivity.this, "Reattrive date : "+date, Toast.LENGTH_SHORT).show();
                    Calendar calendar= Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd");//pattern: dd-mmm-yyyy
                    String date2 = simpleDateFormat.format(calendar.getTime());
                    int d2=Integer.parseInt(date2);
                    progressDialog.dismiss();
                    if (idate==d2)
                    {
                        breakTV.setVisibility(View.VISIBLE);
                        startPlayingBtn.setEnabled(false);
                    }
                    else
                    {
                        startPlayingBtn.setEnabled(true);
                        breakTV.setVisibility(View.INVISIBLE);
                    }


                }
                else{
                    progressDialog.dismiss();
                    date=0;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public  void  progressDialogOpen()
    {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }


}
