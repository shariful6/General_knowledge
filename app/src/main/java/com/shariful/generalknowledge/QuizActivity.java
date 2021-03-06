package com.shariful.generalknowledge;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 15000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;

    private QuestionsLibrary mQuestionsLibrary=new QuestionsLibrary();
    TextView mScoreView;
    TextView mQuestionView;
    TextView viewTV,clickTV;
    TextView countDownTV;
    Button buttonChoice1;
    Button buttonChoice2;
    Button buttonChoice3;
    Button buttonChoice4;
    Button NextBtn;

     String mAnswer;
     int mScore=0;
     int tempScore=0;
     int mQuestionNumber=0;

     int click=0;

    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;

    String uId;
    private FirebaseUser user;


    String retriveViewNum;
    int v;
    String key;

    private AdView madView;
    private InterstitialAd mInterstitialAd;

    ProgressBar pb;
    int counter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        databaseReference= FirebaseDatabase.getInstance().getReference("employee");

        mScoreView=findViewById(R.id.scoreID);
        mQuestionView=findViewById(R.id.questionViewID);
        buttonChoice1=findViewById(R.id.btn1ID);
        buttonChoice2=findViewById(R.id.btn2ID);
        buttonChoice3=findViewById(R.id.btn3ID);
        buttonChoice4=findViewById(R.id.btn4ID);
        NextBtn=findViewById(R.id.nextBtnID);
        madView=findViewById(R.id.adView);
        viewTV=findViewById(R.id.viewId);
        countDownTV=findViewById(R.id.countdownID);
        clickTV=findViewById(R.id.clickID);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uId = user.getUid();

        retriveView();
        retriveScore();
        retriveTempScore();
        retriveClick();
        prog();

        MobileAds.initialize(this,"ca-app-pub-4550007539329197~7201253543"); //  App id//initialize mobile ad

        mInterstitialAd= new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4550007539329197/7939620147");//interstitial

        AdRequest adRequest = new AdRequest.Builder().build();//ad request

        madView.loadAd(adRequest);//load banner ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

            }
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.

            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                click++;
                databaseReference.child(uId).child("click").setValue(String.valueOf(click));
                if (click==2)
                {
                    Toast.makeText(QuizActivity.this, "Wrong Task.. !!Please Wait 24 houres !!", Toast.LENGTH_SHORT).show();
                    databaseReference.child(uId).child("click").setValue(String.valueOf(0));
                    Intent intent = new Intent(QuizActivity.this,MainActivity.class);
                    intent.putExtra("value","1");
                    startActivity(intent);
                }

            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

        });




        buttonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mCountDownTimer.cancel();
                buttonDisbale();
                if(buttonChoice1.getText()==mAnswer)
                {
                    mScore=mScore+5;
                    tempScore=tempScore+5;
                    updateScore(mScore);
                   // updateQuestion();
                    buttonChoice1.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    Toast.makeText(QuizActivity.this, "Correct !!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(QuizActivity.this, "Wrong !! Correct Ans is:"+mAnswer, Toast.LENGTH_LONG).show();
                    buttonChoice1.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrongans_bg));


                    if (buttonChoice1.getText()==mAnswer){
                        buttonChoice1.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                    else if(buttonChoice2.getText()==mAnswer){
                        buttonChoice2.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                    else if(buttonChoice3.getText()==mAnswer){
                        buttonChoice3.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                    else {
                        buttonChoice4.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                   // updateQuestion();
                }


            }

        });
        buttonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  mCountDownTimer.cancel();
                buttonDisbale();
                if(buttonChoice2.getText()==mAnswer)
                {
                    mScore=mScore+5;
                    tempScore=tempScore+5;
                    updateScore(mScore);
                   // updateQuestion();
                    buttonChoice2.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    Toast.makeText(QuizActivity.this, "Correct !!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(QuizActivity.this, "Wrong !! Correct Ans is:"+mAnswer, Toast.LENGTH_LONG).show();
                    buttonChoice2.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrongans_bg));

                    if (buttonChoice1.getText()==mAnswer){
                        buttonChoice1.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                    else if(buttonChoice2.getText()==mAnswer){
                        buttonChoice2.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                    else if(buttonChoice3.getText()==mAnswer){
                        buttonChoice3.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                    else {
                        buttonChoice4.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                   // updateQuestion();
                }
            }
        });
        buttonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  mCountDownTimer.cancel();
                buttonDisbale();
                if(buttonChoice3.getText()==mAnswer)
                {
                    mScore=mScore+5;
                    tempScore=tempScore+5;
                    updateScore(mScore);
                  //  updateQuestion();
                    Toast.makeText(QuizActivity.this, "Correct !!", Toast.LENGTH_SHORT).show();
                    buttonChoice3.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                }
                else{
                    Toast.makeText(QuizActivity.this, "Wrong !! Correct Ans is:"+mAnswer, Toast.LENGTH_LONG).show();
                    buttonChoice3.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrongans_bg));

                    if (buttonChoice1.getText()==mAnswer){
                        buttonChoice1.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                    else if(buttonChoice2.getText()==mAnswer){
                        buttonChoice2.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                    else if(buttonChoice3.getText()==mAnswer){
                        buttonChoice3.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                    else {
                        buttonChoice4.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }

                }
            }
        });
        buttonChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //  mCountDownTimer.cancel();
                buttonDisbale();
                if(buttonChoice4.getText()==mAnswer)
                {
                    mScore=mScore+5;
                    tempScore=tempScore+5;
                    updateScore(mScore);
                  //  updateQuestion();
                    buttonChoice4.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    Toast.makeText(QuizActivity.this, "Correct !!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(QuizActivity.this, "Wrong !! Correct Ans is:"+mAnswer, Toast.LENGTH_LONG).show();
                    buttonChoice4.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrongans_bg));


                    if (buttonChoice1.getText()==mAnswer){
                        buttonChoice1.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                    else if(buttonChoice2.getText()==mAnswer){
                        buttonChoice2.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                    else if(buttonChoice3.getText()==mAnswer){
                        buttonChoice3.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                    else {
                        buttonChoice4.setBackgroundDrawable(getResources().getDrawable(R.drawable.answer_bg));
                    }
                   // updateQuestion();
                }
            }
        });

        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  mCountDownTimer.cancel();
                buttonChoice1.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg2));
                buttonChoice2.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg2));
                buttonChoice3.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg2));
                buttonChoice4.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg2));
                updateQuestion();
                buttonEnable();

            }
        });


        viewTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveView();
            }
        });

    }


    private void updateQuestion()
      {
          counter=0;
         // buttonEnable();
         // NextBtn.setEnabled(false);
     // mTimeLeftInMillis = START_TIME_IN_MILLIS;
     //  startTimer();
     //  updateCountDownText();
          if(mQuestionNumber<=300)
        {

            mQuestionView.setText(mQuestionsLibrary.getQuestion(mQuestionNumber));
            buttonChoice1.setText(mQuestionsLibrary.getChoice1(mQuestionNumber));
            buttonChoice2.setText(mQuestionsLibrary.getChoice2(mQuestionNumber));
            buttonChoice3.setText(mQuestionsLibrary.getChoice3(mQuestionNumber));
            buttonChoice4.setText(mQuestionsLibrary.getChoice4(mQuestionNumber));
            mAnswer=mQuestionsLibrary.getCorrectAnswer(mQuestionNumber);
           if(mQuestionNumber%12==0)
           {
               adLoad();
           }
            saveView();
            mQuestionNumber++;
            viewTV.setText(""+mQuestionNumber);

        }
        else
        {
            Toast.makeText(this, "End all Questions !!!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(QuizActivity.this,ResultActivity.class);
            String sco=String.valueOf(tempScore);
            intent.putExtra("points",sco);

            tempScore=0;
            String tScore=String.valueOf(tempScore);
            databaseReference.child(uId).child("tempScore").setValue(tScore);
            mQuestionNumber=0;
            saveView();
            startActivity(intent);
            finish();

        }


    }


    private void updateScore(int point) {
        mScoreView.setText(""+tempScore);
        String sScore=String.valueOf(mScore);
        String tScore=String.valueOf(tempScore);
        databaseReference.child(uId).child("score").setValue(sScore);
        databaseReference.child(uId).child("tempScore").setValue(tScore);

    }

    public void retriveScore()
    {
        databaseReference.child(uId).child("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String rScore = dataSnapshot.getValue(String.class);
                    // mScoreView.setText(rScore);
                    int sc=Integer.parseInt(rScore);
                    mScore=sc;

                }
                else{

                    // mScoreView.setText("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(QuizActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void retriveClick()
    {
        databaseReference.child(uId).child("click").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String clickNumber = dataSnapshot.getValue(String.class);

                    int rClick=Integer.parseInt(clickNumber);
                    click=rClick;
                    clickTV.setText("Invalid Click: "+click);


                }
                else{

                    // mScoreView.setText("0");
                    click=0;
                    clickTV.setText("Invalid Click: "+click);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(QuizActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void retriveTempScore()
    {
        databaseReference.child(uId).child("tempScore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String tScore = dataSnapshot.getValue(String.class);
                    mScoreView.setText(tScore);
                    int ts=Integer.parseInt(tScore);
                    tempScore=ts;

                }
                else{

                    mScoreView.setText("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(QuizActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void retriveView()
    {

        databaseReference.child(uId).child("view").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    retriveViewNum = dataSnapshot.getValue(String.class);

                   viewTV.setText(retriveViewNum);

                  v=Integer.parseInt(retriveViewNum);
                   mQuestionNumber=v;
                   // Toast.makeText(QuizActivity.this, "view: "+mQuestionNumber, Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
                else{
                    //Toast.makeText(WorkActivity.this, "00", Toast.LENGTH_SHORT).show();
                    viewTV.setText("0");
                    mQuestionNumber=0;
                    updateQuestion();
                    Toast.makeText(QuizActivity.this, "view: 0", Toast.LENGTH_SHORT).show();
                   // databaseReference.child(uId).child("view").setValue("0");

                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(QuizActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Timer Section  mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning=false;
            }
        }.start();

        // mTimerRunning = false;
    }
    private void updateCountDownText() {

        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d",seconds);

        countDownTV.setText(timeLeftFormatted);

        if(seconds==00)
        {
           buttonDisbale();
            NextBtn.setEnabled(true);

        }
        else {
            buttonEnable();
            NextBtn.setEnabled(false);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    public void buttonDisbale()
    {
        buttonChoice1.setEnabled(false);
        buttonChoice2.setEnabled(false);
        buttonChoice3.setEnabled(false);
        buttonChoice4.setEnabled(false);
    }
   public void buttonEnable()
   {
       buttonChoice1.setEnabled(true);
       buttonChoice2.setEnabled(true);
       buttonChoice3.setEnabled(true);
       buttonChoice4.setEnabled(true);
   }

   public void saveView()
   {
       String viewNum = String.valueOf(mQuestionNumber);
       databaseReference.child(uId).child("view").setValue(viewNum);
   }


   public void adLoad() {

       if(mInterstitialAd.isLoaded()) //mQuestionNumber%5==0
       {
           mInterstitialAd.show();
       }

   }


   public  void prog()
   {
       pb = (ProgressBar)findViewById(R.id.progressbarID);

       final Timer t = new Timer();
       TimerTask tt = new TimerTask() {
           @Override
           public void run()
           {
               counter++;
               pb.setProgress(counter);

               if(counter == 200)
               {

                   t.cancel();
                  // buttonDisbale();
                  // NextBtn.setEnabled(true);

               }

           }
       };

       t.schedule(tt,0,200);




   }


}
