package com.example.moviesapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class main2 extends AppCompatActivity {
private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        progressBar=findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.progress), PorterDuff.Mode.SRC_IN );


        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
            try{
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                Intent intent=new Intent(main2.this,MainActivity.class);
                startActivity(intent);
            }
            }
        });
    thread.start();
    }
}
