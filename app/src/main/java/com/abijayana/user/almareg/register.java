package com.abijayana.user.almareg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by user on 31-07-2016.
 */
public class register extends AppCompatActivity {
    Spinner sc1,sc2,sc3; int y;Button register;SharedPreferences pol,sp;
    String[] a={"","MM","ME","CS","CE","EE","EC"};String t1="CHOICE 1";String t2="CHOICE 2";String roll="";String t3="GENDER";
    ArrayList<String> b=new ArrayList<String>(Arrays.asList("","010","020"));String nme;EditText ed1,ed2;
    ArrayList<String> c=new ArrayList<String>();Firebase fr;
    ArrayAdapter<String> c1,c2,c3;
    ArrayList<String> ch1=new ArrayList<String>(Arrays.asList("CHOICE 1","PUBLICITY","WEB DESIGN","DND","EVENTS","SPONSORSHIP"));
    ArrayList<String> ch2=new ArrayList<String>(Arrays.asList("CHOICE 2","PUBLICITY","WEB DESIGN","DND","EVENTS","SPONSORSHIP"));
    ArrayList<String> ch3=new ArrayList<String>(Arrays.asList("GENDER","MALE","FEMALE"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getApplicationContext());
        setContentView(R.layout.rgstr);
         initialize();

         c1=new ArrayAdapter<String>(register.this,R.layout.spinner1,ch1) ;
         c2=new ArrayAdapter<String>(register.this,R.layout.spinner1,ch2) ;
         c3=new ArrayAdapter<String>(register.this,R.layout.spinner1,ch3);
         sc1.setAdapter(c1);sc2.setAdapter(c2);sc3.setAdapter(c3);
         checkeverything2();edtest(ed1);edtest(ed2);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkemptyt();
            }
        });



    }
    public void register_stdnt() {
        register.setClickable(false);
        register.setText("Wait...");
        Toast.makeText(register.this,"Please Wait..",Toast.LENGTH_SHORT).show();
        roll=ed2.getText().toString();
        fr = new Firebase("https://almareg.firebaseio.com");
        fr.child("freshers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final long rt = dataSnapshot.getChildrenCount();
                HashMap<String, String> cv = new HashMap<String, String>();
                cv.put(String.valueOf(rt), null);
                fr.child("freshers").push().setValue(cv, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null)
                            Toast.makeText(register.this, "ERROR", Toast.LENGTH_SHORT).show();
                        else {
                            String t = pol.getString("ABHA", "BOY");
                            String ty = sp.getString("HAI", "BOY");
                            HashMap<String, Object> ui = new HashMap<String, Object>();
                            ui.put("rollno",roll);
                            ui.put("name", nme);
                            ui.put("choice1", t1);
                            ui.put("choice2", t2);
                            ui.put("gender",t3);
                            ui.put("usrnme", ty);
                            ui.put("usrid", t);
                            fr.child("freshers").child(String.valueOf(rt)).updateChildren(ui, new Firebase.CompletionListener() {
                                @Override
                                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                    if (firebaseError != null)
                                        Toast.makeText(register.this, "ERROR", Toast.LENGTH_SHORT).show();
                                    else {register.setClickable(true);

                                        Toast.makeText(register.this,"REGISTERED",Toast.LENGTH_SHORT).show();
                                        register.setText("REGISTED");
                                        try {
                                            Thread.sleep(3000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        ed1.setText("");
                                        ed2.setText("");
                                        register.setText("REGISTER");
                                        Toast.makeText(register.this,"REGISTERED",Toast.LENGTH_LONG).show();



                                    }



                                }
                            });


                        }
                    }
                });


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void checkemptyt(){
        nme=ed1.getText().toString();roll=ed2.getText().toString();
        if(nme.isEmpty())ed1.setError("Fill Properly");
        else {
            if ((roll.isEmpty()) || (roll.length() < 9)) ed2.setError("Fill Properly");
            else {
                if ((t1.compareTo("CHOICE 1") == 0) || (t2.compareTo("CHOICE 2") == 0)||(t3.compareTo("GENDER")==0))
                    Toast.makeText(register.this, "Choose Options", Toast.LENGTH_SHORT).show();
                else register_stdnt();


            }
        }




    }



    private void checkeverything2() {

sc1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int h = sc1.getPositionForView(view);
        t1 = String.valueOf(sc1.getItemAtPosition(h));
        if (h != 0) {
            ch2.clear();
            ch2.add("CHOICE 2");
            ch2.add("PUBLICITY");
            ch2.add("WEB DESIGN");
            ch2.add("DND");
            ch2.add("EVENTS");
            ch2.add("SPONSORSHIP");

            ch2.remove(h);
        } else {
            ch2.clear();
            ch2.add("CHOICE 2");
            ch2.add("PUBLICITY");
            ch2.add("WEB DESIGN");
            ch2.add("DND");
            ch2.add("EVENTS");
            ch2.add("SPONSORSHIP");

        }
        c2.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
        sc2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int h1=sc2.getPositionForView(view);
                t2= String.valueOf(sc2.getItemAtPosition(h1));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sc3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int h3=sc3.getPositionForView(view);
                t3=String.valueOf(sc3.getItemAtPosition(h3));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






    }




    public void initialize(){

         sc1=(Spinner)findViewById(R.id.spinner) ;
         sc2=(Spinner)findViewById(R.id.spinner3);
        sc3=(Spinner)findViewById(R.id.spinner2);
         register=(Button)findViewById(R.id.buttlgin);
        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);

        pol = this.getSharedPreferences("ABHAYA", Context.MODE_PRIVATE);
        sp=this.getSharedPreferences("AMMAACHAN", Context.MODE_PRIVATE);
        String ab=sp.getString("HAI","BOY");

    }
    public void edtest(final EditText ed){

        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((ed.getText().toString()).compareTo("")==0)ed.setHint("");
            }
        });

    }

}
