package com.abijayana.user.almareg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;

/**
 * Created by user on 26-07-2016.
 */
public class createact extends AppCompatActivity {
    Firebase fr; EditText ed;  Button go; Button anim;TextView tv;SharedPreferences pol,sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        fr=new Firebase("https://almareg.firebaseio.com/users");
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.usracnt);
        login.fa.finish();
        go=(Button)findViewById(R.id.butusercrte);
        anim=(Button)findViewById(R.id.ldngpass);
        tv=(TextView)findViewById(R.id.safty);
        pol = this.getSharedPreferences("ABHAYA", Context.MODE_PRIVATE);
        sp=this.getSharedPreferences("AMMAACHAN", Context.MODE_PRIVATE);
        anim.setVisibility(View.INVISIBLE);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    fun();
                    String id=pol.getString("ABHA","BOY");
                    String name=sp.getString("HAI","User");
                    create_new_user(id,name);
            }




        });





    }
    public void fun(){
        anim.setVisibility(View.VISIBLE);
        Animation anm= AnimationUtils.loadAnimation(createact.this,R.anim.anime);
        anim.startAnimation(anm);
        Toast.makeText(createact.this,"Working...",Toast.LENGTH_LONG).show();
        go.setVisibility(View.INVISIBLE);
        tv.setText("Please Wait...");
    }
   public void create_new_user(final String id, final String name){

       HashMap<String,String> a1=new HashMap<String,String>();
       a1.put(id,null);
       fr.push().setValue(a1, new Firebase.CompletionListener() {
           @Override
           public void onComplete(FirebaseError firebaseError, Firebase firebase) {
               if(firebaseError!=null)Toast.makeText(createact.this,"ERROR",Toast.LENGTH_SHORT).show();
               else{
                   HashMap<String,Object> a2=new HashMap<String, Object>();
                   a2.put("id",id);
                   a2.put("name",name);
                   fr.child(id).updateChildren(a2, new Firebase.CompletionListener() {
                       @Override
                       public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                           if(firebaseError!=null)Toast.makeText(createact.this,"ERROR",Toast.LENGTH_SHORT).show();
                           else{
                               HashMap<String,String> a3=new HashMap<String, String>();
                               a3.put("recieve",null);
                               fr.child(id).push().setValue(a3, new Firebase.CompletionListener() {
                                   @Override
                                   public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                       if(firebaseError!=null)Toast.makeText(createact.this,"ERROR",Toast.LENGTH_SHORT).show();
                                       else {
                                           HashMap<String,String> a4=new HashMap<String, String>();
                                           a4.put("0",null);
                                           fr.child(id).child("recieve").push().setValue(a4, new Firebase.CompletionListener() {
                                               @Override
                                               public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                                   if(firebaseError!=null)Toast.makeText(createact.this,"ERROR",Toast.LENGTH_SHORT).show();
                                                   else{
                                                       HashMap<String,Object> a5=new HashMap<String,Object>();
                                                       a5.put("id","100009604837097");
                                                       a5.put("sender","App");
                                                       fr.child(id).child("recieve").child("0").updateChildren(a5, new Firebase.CompletionListener() {
                                                           @Override
                                                           public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                                               if(firebaseError!=null)Toast.makeText(createact.this,"ERROR",Toast.LENGTH_SHORT).show();
                                                               else{
                                                                   HashMap<String,String> a6=new HashMap<String, String>();
                                                                   a6.put("messages",null);
                                                                   fr.child(id).child("recieve").child("0").push().setValue(a6, new Firebase.CompletionListener() {
                                                                       @Override
                                                                       public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                                                           if(firebaseError!=null)Toast.makeText(createact.this,"ERROR",Toast.LENGTH_SHORT).show();
                                                                           else{

                                                                               HashMap<String,Object> a7=new HashMap<String, Object>();
                                                                               a7.put("0","");
                                                                               a7.put("1","Please clear" +
                                                                                       "your doubt " +
                                                                                       "through TEAM");
                                                                               a7.put("2","Yo Alma");
                                                                               fr.child(id).child("recieve").child("0").child("messages").updateChildren(a7, new Firebase.CompletionListener() {
                                                                                   @Override
                                                                                   public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                                                                       if(firebaseError!=null)Toast.makeText(createact.this,"ERROR",Toast.LENGTH_SHORT).show();
                                                                                       else{
                                                                                           Toast.makeText(createact.this,"SUCCESS",Toast.LENGTH_SHORT).show();
                                                                                           try {
                                                                                               Class g=Class.forName("com.abijayana.user.almareg.MainActivity");
                                                                                               Intent i=new Intent(createact.this,g);
                                                                                               startActivity(i);
                                                                                           } catch (ClassNotFoundException e) {
                                                                                               e.printStackTrace();
                                                                                           }
                                                                                       }

                                                                                   }
                                                                               });
                                                                           }
                                                                       }
                                                                   });


                                                               }
                                                           }
                                                       });


                                                   }
                                               }
                                           });

                                       }

                                   }
                               });
                           }
                       }
                   });

               }
           }
       });



   }
}
