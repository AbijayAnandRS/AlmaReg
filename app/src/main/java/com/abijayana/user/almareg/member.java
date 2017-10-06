package com.abijayana.user.almareg;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by user on 03-08-2016.
 */
public class member extends AppCompatActivity {
    SharedPreferences hy,fg;String name,no;TextView tv;Button a1,a2;LinearLayout lk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        lk=(LinearLayout)findViewById(R.id.ftyu);

        hy=this.getSharedPreferences("CHATNME", Context.MODE_PRIVATE);
        fg=this.getSharedPreferences("PHONE",Context.MODE_PRIVATE);
        tv=(TextView)findViewById(R.id.memname);
        name=hy.getString("NAME","HAI");
        no=fg.getString("NO","8281810667");
        if(no.compareTo("7205749013")==0)lk.setBackgroundResource(R.mipmap.kanisque);
        else if(no.compareTo("8280149584")==0)lk.setBackgroundResource(R.mipmap.abinav);
        else if(no.compareTo("8280180810")==0)lk.setBackgroundResource(R.mipmap.kushagr);
        else if(no.compareTo("9420747191")==0)lk.setBackgroundResource(R.mipmap.chendu);
        else if(no.compareTo("1234567890")==0)lk.setBackgroundResource(R.mipmap.rishu);

        tv.setText(name);

        a2=(Button)findViewById(R.id.chatting);

        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class g=Class.forName("com.abijayana.user.almareg.chat");
                    Intent i=new Intent(member.this,g);
                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
