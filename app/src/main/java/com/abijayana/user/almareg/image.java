package com.abijayana.user.almareg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.ProfilePictureView;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 28-07-2016.
 */
public class image extends AppCompatActivity {
    long ra;
ArrayList<nwsfeed> list2;ListView lv2;String url;ProfilePictureView ppv;EditText edf;Button btn;
    comment_adapter adp;Firebase fr;SharedPreferences s,d,pol,sp;int pos;Long r;ImageView iv;String id,name;TextView lk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.picture);
        iv=(ImageView)findViewById(R.id.imaaa);
        lv2=(ListView)findViewById(R.id.lsvw4);
        lk=(TextView)findViewById(R.id.likes);
        ppv=(ProfilePictureView)findViewById(R.id.ivop);
        edf=(EditText)findViewById(R.id.ed1111);
        btn=(Button)findViewById(R.id.sendfgf);
        fr=new Firebase("https://almareg.firebaseio.com/newsfeed");

        list2=new ArrayList<nwsfeed>();
        adp=new comment_adapter(image.this,R.layout.list1,list2);
        lv2.setAdapter(adp);
        s=this.getSharedPreferences("SSD", Context.MODE_PRIVATE);
        d=this.getSharedPreferences("DDS", Context.MODE_PRIVATE);
        pol = this.getSharedPreferences("ABHAYA", Context.MODE_PRIVATE);
        sp=this.getSharedPreferences("AMMAACHAN", Context.MODE_PRIVATE);
         id=pol.getString("ABHA", "1234567890");
        name=sp.getString("HAI", "User");
        pos = s.getInt("Pos", 0);
        url=d.getString("imurl", "abiujay");
        if(url.compareTo("")!=0) Picasso.with(image.this).load(url).placeholder(R.drawable.rotate).into(iv);
        ppv.setProfileId(id);
        edf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edf.getText().toString().compareTo("") == 0) edf.setHint("");
            }
        });
        fr.child(String.valueOf(pos)).child("likes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long likes = dataSnapshot.getChildrenCount();
                lk.setText(likes + " Likes");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(image.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edf.getText().toString().compareTo("") == 0)
                    Toast.makeText(image.this, "Please Type Comment", Toast.LENGTH_SHORT).show();
                else {
                    btn.setVisibility(View.INVISIBLE);
                    final String comment = edf.getText().toString();
                    fr.child(String.valueOf(pos)).child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            r = dataSnapshot.getChildrenCount();
                            HashMap<String, String> hn = new HashMap<String, String>();
                            hn.put(String.valueOf(r), null);
                            fr.child(String.valueOf(pos)).child("comments").push().setValue(hn, new Firebase.CompletionListener() {
                                @Override
                                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                    if (firebaseError != null)
                                        Toast.makeText(image.this, "ERROR", Toast.LENGTH_SHORT).show();
                                    else {

                                        HashMap<String, Object> dfg = new HashMap<String, Object>();
                                        dfg.put("comment", comment);
                                        dfg.put("id", id);
                                        dfg.put("name", name);
                                        fr.child(String.valueOf(pos)).child("comments").child(String.valueOf(r)).updateChildren(dfg, new Firebase.CompletionListener() {
                                            @Override
                                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                                if (firebaseError != null)
                                                    Toast.makeText(image.this, "ERROR", Toast.LENGTH_SHORT).show();

                                                btn.setVisibility(View.VISIBLE);
                                                edf.setText("");

                                            }
                                        });

                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Toast.makeText(image.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });
        fr=new Firebase("https://almareg.firebaseio.com/newsfeed");
        lk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fr.child(String.valueOf(pos)).child("likes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long ty = dataSnapshot.getChildrenCount();
                        if ((String.valueOf(dataSnapshot.child(id).child("name").getValue())).compareTo(name) == 0) {
                            fr.child(String.valueOf(pos)).child("likes").child(id).child("name").removeValue(new Firebase.CompletionListener() {
                                @Override
                                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                    if (firebaseError != null)
                                        Toast.makeText(image.this, "ERROR", Toast.LENGTH_SHORT).show();



                                }
                            });
                        } else {
                            HashMap<String, String> bn = new HashMap<String, String>();
                            bn.put(id, null);
                            fr.child(String.valueOf(pos)).child("likes").push().setValue(bn, new Firebase.CompletionListener() {
                                @Override
                                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                    if (firebaseError != null)
                                        Toast.makeText(image.this, "ERROR", Toast.LENGTH_SHORT).show();
                                    else {
                                        HashMap<String, Object> ol = new HashMap<String, Object>();
                                        ol.put("name", name);
                                        fr.child(String.valueOf(pos)).child("likes").child(id).updateChildren(ol, new Firebase.CompletionListener() {
                                            @Override
                                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                                if (firebaseError != null)
                                                    Toast.makeText(image.this, "ERROR", Toast.LENGTH_SHORT).show();



                                            }
                                        });


                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });

        fr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ra = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        fr.child(String.valueOf(pos)).child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long r = dataSnapshot.getChildrenCount();
                list2.clear();
                for (long i = 0; i <= dataSnapshot.getChildrenCount()-1; i++) {
                    nwsfeed nd = new nwsfeed();
                    nd.setCidnme(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("name").getValue()));
                    nd.setComment(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("comment").getValue()));
                    nd.setIdcomment(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("id").getValue()));
                    list2.add(nd);

                }
                adp.notifyDataSetChanged();
                lv2.smoothScrollToPosition(list2.size()-1);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(image.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        fr.child(String.valueOf(pos)).child("comments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adp.notifyDataSetChanged();
                lv2.smoothScrollToPosition(list2.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                adp.notifyDataSetChanged();
                lv2.smoothScrollToPosition(list2.size() - 1);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adp.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                adp.notifyDataSetChanged();
                lv2.smoothScrollToPosition(list2.size()-1);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(image.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        lv2.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
                return true;
            }
        });










    }
    public void customLoadMoreDataFromApi(int page) {
        // Send an API request to retrieve appropriate data using the offset value as a parameter.
        //  --> Deserialize API response and then construct new objects to append to the adapter
        //  --> Notify the adapter of the changes
    }


}
