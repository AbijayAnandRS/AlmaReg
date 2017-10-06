package com.abijayana.user.almareg;

/**
 * Created by user on 01-08-2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.ProfilePictureView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class chat extends Activity {
    ListView lv2;
    ArrayList<nwsfeed> list2;
    chatAdapter cdp;
    Button b1;
    int yuo=0;
    long hn;

    EditText chate;
    Firebase gh;
    Firebase hk;

    String df;String kldfy;
    SharedPreferences jk,mn,sp,kl,ps,ikl,pol,idpr;
    Firebase fb;
    int pos;
    int check=0;int ullil;long sd=0,ds=0;
    long kk,mypos;
    String name, image,prid;
    ValueEventListener listtnr;
    Thread t;
    int y=0;int hhh=0;
    long jklo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(getApplicationContext());




        setContentView(R.layout.chat);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }
        lv2=(ListView)findViewById(R.id.listviewchat);
        chate=(EditText)findViewById(R.id.editttchat);
        b1=(Button)findViewById(R.id.chatsend);
        list2 = new ArrayList<nwsfeed>();
        b1.setVisibility(View.INVISIBLE);
        fb=new Firebase("https://almareg.firebaseio.com/users");

        jk=this.getSharedPreferences("CHATNME", Context.MODE_PRIVATE);
        mn=this.getSharedPreferences("POSITION", Context.MODE_PRIVATE);
        sp=this.getSharedPreferences("AMMAACHAN", Context.MODE_PRIVATE);
        kl=this.getSharedPreferences("ABIJAY", Context.MODE_PRIVATE);
        ps=this.getSharedPreferences("ACHANAMMA", Context.MODE_PRIVATE);
        ikl=this.getSharedPreferences("chtige", Context.MODE_PRIVATE);
        pol=this.getSharedPreferences("ABHAYA",Context.MODE_PRIVATE);
        idpr=this.getSharedPreferences("CHATID",Context.MODE_PRIVATE);
        kldfy=idpr.getString("ID","12345");
        prid=pol.getString("ABHA","455667");
        mypos=ps.getLong("BOY", 9);
        df=jk.getString("NAME", "App");
        String ef=ikl.getString("IMGE","hai");
        ProfilePictureView in=(ProfilePictureView)findViewById(R.id.chatimage);
        TextView klo=(TextView)findViewById(R.id.chatname);
        klo.setText(df);
        in.setProfileId(kldfy);
        pos=mn.getInt("POS", 0);
        name = sp.getString("HAI", "User");

        try {
            HotorNot hij = new HotorNot(getApplicationContext(), kldfy);
            hij.open();



            list2 = hij.getDATA();
            hij.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        cdp=new chatAdapter(getApplicationContext(),R.layout.chatsingle,list2);
        lv2.setAdapter(cdp);
        t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(800);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(yuo==1) {
                                    fb.child(prid).child("recieve").addListenerForSingleValueEvent(new ValueEventListener() {


                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {


                                            for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {

                                                if (((String.valueOf(dataSnapshot.child(String.valueOf(i)).child("sender").getValue())).compareTo(df)) == 0) {

                                                    long hj = dataSnapshot.child(String.valueOf(i)).child("messages").getChildrenCount();


                                                    for (long kp = 1; kp < hj; kp++) {


                                                        String messge = String.valueOf(dataSnapshot.child(String.valueOf(i)).child("messages").child(String.valueOf(kp)).getValue());
                                                        HotorNot entry = new HotorNot(getApplicationContext(), kldfy);
                                                        entry.open();
                                                        if (messge == null) {
                                                        } else {
                                                            entry.createEntry(messge, 2);
                                                        }

                                                        entry.close();
                                                        HotorNot hij = new HotorNot(getApplicationContext(), kldfy);
                                                        hij.open();
                                                        list2 = hij.getDATA();
                                                        hij.close();

                                                        fb.child(prid).child("recieve").child(String.valueOf(i)).child("messages").child(String.valueOf(kp)).removeValue(new Firebase.CompletionListener() {
                                                            @Override
                                                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                                                if (firebaseError != null) {
                                                                    Toast.makeText(chat.this, "ERROR", Toast.LENGTH_SHORT).show();
                                                                } else {

                                                                    cdp = new chatAdapter(getApplicationContext(), R.layout.chatsingle, list2);
                                                                    lv2.setAdapter(cdp);


                                                                }

                                                            }
                                                        });

                                                    }

                                                }
                                            }


                                        }


                                        @Override
                                        public void onCancelled(FirebaseError firebaseError) {

                                        }
                                    });
                                }



                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
        if(yuo==0)
        {
            fb.child(prid).child("recieve").addListenerForSingleValueEvent(new ValueEventListener() {


                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {

                        if (((String.valueOf(dataSnapshot.child(String.valueOf(i)).child("sender").getValue())).compareTo(df)) == 0) {

                            long hj = dataSnapshot.child(String.valueOf(i)).child("messages").getChildrenCount();


                            for (long kp = 1; kp < hj; kp++) {


                                String messge = String.valueOf(dataSnapshot.child(String.valueOf(i)).child("messages").child(String.valueOf(kp)).getValue());
                                HotorNot entry = new HotorNot(getApplicationContext(), kldfy);
                                entry.open();
                                if (messge == null) {
                                } else {
                                    entry.createEntry(messge, 2);
                                }

                                entry.close();
                                HotorNot hij = new HotorNot(getApplicationContext(), kldfy);
                                hij.open();
                                list2 = hij.getDATA();
                                hij.close();

                                fb.child(prid).child("recieve").child(String.valueOf(i)).child("messages").child(String.valueOf(kp)).removeValue(new Firebase.CompletionListener() {
                                    @Override
                                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                        if (firebaseError != null) {
                                            Toast.makeText(chat.this, "ERROR", Toast.LENGTH_SHORT).show();
                                        } else {

                                            cdp = new chatAdapter(getApplicationContext(), R.layout.chatsingle, list2);
                                            lv2.setAdapter(cdp);


                                        }

                                    }
                                });

                            }

                        }
                    }


                }


                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            yuo=1;

        }





        listtnr=  fb.child(kldfy).child("recieve").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hn = dataSnapshot.getChildrenCount();

                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    if (((String.valueOf(dataSnapshot.child(String.valueOf(i)).child("id").getValue())).compareTo(prid)) == 0) {
                        check = 1;
                        kk = dataSnapshot.child(String.valueOf(i)).child("messages").getChildrenCount();

                        ullil = i;
                        break;
                    } else {
                        check = 2;
                    }

                }

                sd = dataSnapshot.getChildrenCount();


                if ((sd > 0) && (check > 0) && (hhh == 0)) {
                    b1.setVisibility(View.VISIBLE);
                    hhh = 1;
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(chat.this, "ERROR", Toast.LENGTH_SHORT).show();

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {




            String dfg;

            long y;
            long r;
            long x;
            long ta=hn;

            @Override
            public void onClick(View v) {
                b1.setVisibility(View.INVISIBLE);
                y=0;


                image = kl.getString("TA", "KL");
                dfg = chate.getText().toString();
                HotorNot entry = new HotorNot(getApplicationContext(), kldfy);
                entry.open();
                entry.createEntry(dfg, 1);
                entry.close();
                HotorNot hijq = new HotorNot(getApplicationContext(), kldfy);
                hijq.open();
                list2 = hijq.getDATA();
                hijq.close();
                cdp = new chatAdapter(getApplicationContext(), R.layout.chatsingle, list2);
                lv2.setAdapter(cdp);

                y = sd;
                r = ullil;
                x = kk;
                if (check == 1) {
                    HashMap<String, Object> ui = new HashMap<String, Object>();
                    ui.put(String.valueOf(kk), dfg);
                    fb.child(kldfy).child("recieve").child(String.valueOf(r)).child("messages").updateChildren(ui, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null) {
                                Toast.makeText(chat.this, "ERROR1", Toast.LENGTH_SHORT).show();
                            } else {


                                Toast.makeText(chat.this, "SEND", Toast.LENGTH_SHORT).show();
                                chate.setText("");
                                b1.setVisibility(View.VISIBLE);


                            }

                        }
                    });


                } else if (check == 2) {
                    if (sd > 0) {
                        HashMap<String, String> uj = new HashMap<String, String>();
                        uj.put(String.valueOf(sd), null);
                        fb.child(kldfy).child("recieve").push().setValue(uj, new Firebase.CompletionListener() {
                            @Override
                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                if (firebaseError != null) {
                                    Toast.makeText(chat.this, "ERROR1", Toast.LENGTH_SHORT).show();
                                } else {
                                    HashMap<String, Object> hj = new HashMap<String, Object>();

                                    hj.put("sender", name);
                                    hj.put("id", prid);
                                    fb.child(kldfy).child("recieve").child(String.valueOf(y)).updateChildren(hj, new Firebase.CompletionListener() {
                                        @Override
                                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                            if (firebaseError != null) {
                                                Toast.makeText(chat.this, "ERROR2", Toast.LENGTH_SHORT).show();
                                            } else {
                                                HashMap<String, String> nm = new HashMap<String, String>();
                                                nm.put("messages", null);
                                                fb.child(kldfy).child("recieve").child(String.valueOf(y)).push().setValue(nm, new Firebase.CompletionListener() {
                                                    @Override
                                                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                                        if (firebaseError != null) {
                                                            Toast.makeText(chat.this, "ERROR3", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            HashMap<String, Object> hy = new HashMap<>();
                                                            hy.put("0","");
                                                            hy.put("1",dfg);

                                                            fb.child(kldfy).child("recieve").child(String.valueOf(y)).child("messages").updateChildren(hy, new Firebase.CompletionListener() {
                                                                @Override
                                                                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                                                    if (firebaseError != null) {
                                                                        Toast.makeText(chat.this, "ERROR4", Toast.LENGTH_SHORT).show();
                                                                    } else {

                                                                    /*
                                                                        HashMap<String, Object> kli = new HashMap<String, Object>();
                                                                        kli.put("message", "");
                                                                        fb.child(kldfy).child("recieve").child(String.valueOf(y)).child("messages").child("-KELqW6fOeDWAzZ3GWNk").updateChildren(kli, new Firebase.CompletionListener() {
                                                                            @Override
                                                                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                                                                if (firebaseError != null) {
                                                                                    Toast.makeText(chat.this, "ERROR4", Toast.LENGTH_SHORT).show();
                                                                                } else {/*
                                                                                    HashMap<String, Object> hyo = new HashMap<String, Object>();
                                                                                    hyo.put("message", dfg);
                                                                                    fb.child("1565695407093939").child("recieve").child(String.valueOf(y)).child("messages").push().setValue(hyo, new Firebase.CompletionListener() {
                                                                                        @Override
                                                                                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                                                                            if (firebaseError != null) {
                                                                                                Toast.makeText(chat.this, "ERROR4", Toast.LENGTH_SHORT).show();
                                                                                            } else {*/

                                                                                                Toast.makeText(chat.this, "SEND", Toast.LENGTH_SHORT).show();
                                                                                                chate.setText("");
                                                                                                b1.setVisibility(View.VISIBLE);
                                                                                                y = 0;/*
                                                                                            }
                                                                                        }
                                                                                    });

                                                                                            }

                                                                            }
                                                                        });*/

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


            }
        });







    }
    @Override
    public void onBackPressed() {

        fb.child("1565695407093939").child("recieve").removeEventListener(listtnr);
        t.interrupt();
        this.finish();

    }


}

