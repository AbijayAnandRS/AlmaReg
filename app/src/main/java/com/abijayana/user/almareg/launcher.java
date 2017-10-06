package com.abijayana.user.almareg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by user on 25-07-2016.
 */

public class launcher extends AppCompatActivity {
    ConnectivityManager cm;SharedPreferences df;int a;
    TextView zno_network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_error);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#3F51B5"));
        }
        zno_network = (TextView)findViewById(R.id.zno_network);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/robothin.ttf");
        zno_network.setTypeface(face);

        df=this.getSharedPreferences("ASD",Context.MODE_PRIVATE);
        a=df.getInt("NUM",1);
        cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni=cm.getActiveNetworkInfo();
        if(ni==null) Toast.makeText(launcher.this,"No Network",Toast.LENGTH_LONG).show();
        else {
            if(a==1)goclass("login");
            else goclass("MainActivity");




        }










    }
    public void goclass(String classname){
        Intent i;
        try {
            Class g=Class.forName("com.abijayana.user.almareg."+classname);
            i=new Intent(launcher.this,g);
            startActivity(i);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }
}
