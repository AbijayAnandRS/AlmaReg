package com.abijayana.user.almareg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by user on 02-08-2016.
 */
public class Abijay extends AppCompatActivity {
    TabLayout tabLayout;Button b1,b2,b3,b4,b5;
    static View rootView1;
    static View rootView2;ListView lv;
    LayoutInflater l1,l2;   FloatingActionButton fab;
    public SectionsPagerAdapter mSectionsPagerAdapter;
    ArrayList<nwsfeed> list;Firebase fr;

    public ViewPager mViewPager;
    recieceAdaptr adpa;SharedPreferences pol,sp,fg,tg,hy;String myid,mynme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.members);
        pol = this.getSharedPreferences("ABHAYA", Context.MODE_PRIVATE);
        sp=this.getSharedPreferences("AMMAACHAN", Context.MODE_PRIVATE);
        fg=this.getSharedPreferences("PHONE",Context.MODE_PRIVATE);
        tg=this.getSharedPreferences("CHATID",Context.MODE_PRIVATE);
        hy=this.getSharedPreferences("CHATNME",Context.MODE_PRIVATE);
        myid=pol.getString("ABHA","YA");
        mynme=sp.getString("HAI","HAI");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#3b5998"));
        }
        fr=new Firebase("https://almareg.firebaseio.com/users");



        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        l1=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        l2=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView1=l1.inflate(R.layout.team,null);
        rootView2=l2.inflate(R.layout.p2,null);
        lv=(ListView)rootView2.findViewById(R.id.lsvwo);
        list=new ArrayList<nwsfeed>();
        adpa=new recieceAdaptr(Abijay.this,R.layout.single1,list);
        lv.setAdapter(adpa);
        button_take();button_execute();
        fr.child(myid).child("recieve").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();
                for (long k = dataSnapshot.getChildrenCount() - 1; k >= 0; k--) {
                    nwsfeed no = new nwsfeed();
                    no.setName(String.valueOf(dataSnapshot.child(String.valueOf(k)).child("sender").getValue()));
                    no.setIdcomment(String.valueOf(dataSnapshot.child(String.valueOf(k)).child("id").getValue()));
                    long hj = dataSnapshot.child(String.valueOf(k)).child("messages").getChildrenCount();
                    no.setNotify(hj);
                    no.setComment(String.valueOf(dataSnapshot.child(String.valueOf(k)).child("messages").child(String.valueOf(hj - 1)).getValue()));
                    list.add(no);
                }
                adpa.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                error();
            }
        });

        fr.child(myid).child("recieve").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adpa.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                adpa.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adpa.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                adpa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                error();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String iop=list.get(position).getIdcomment();
                String pov=list.get(position).getName();
                SharedPreferences.Editor as=tg.edit();as.putString("ID", iop);as.commit();
                SharedPreferences.Editor nm=hy.edit();nm.putString("NAME",pov);nm.commit();
                goclass("chat");
            }
        });








    }
    public  static class abi1 extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            return rootView1;
        }

    }
    public static class abi2 extends Fragment{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            return rootView2;
        }

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:

                    return new abi1();


                case 1:

                    return new abi2();

                default:

                    return null;
            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "TEAM";
                case 1:
                    return "RECIEVED";

            }
            return null;
        }

    }
    public void error(){
        Toast.makeText(Abijay.this,"ERROR",Toast.LENGTH_SHORT).show();

    }

    public void button_take(){
        b1=(Button)rootView1.findViewById(R.id.publi);
        b2=(Button)rootView1.findViewById(R.id.webd);
        b3=(Button)rootView1.findViewById(R.id.dnd);
        b4=(Button)rootView1.findViewById(R.id.spons);
        b5=(Button)rootView1.findViewById(R.id.evnts);
    }
    public void button_execute(){
       b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               go_next("8280180810","1727323120850092","Kushagra Pandey");
               goclass("member");
           }
       });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               go_next("7205749013","1219679194749610","Kanisque Meena");
               goclass("member");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_next("1234567890","1807078969527134","Rishu Raj");
                goclass("member");
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_next("8280149584","1202375533105872","Abhinav Shukla");
                goclass("member");
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_next("9420747191","624690781032003","Rushikesh Ahire");
                goclass("member");

            }
        });

    }
    public void go_next(String noe,String id,String name){
        SharedPreferences.Editor rf=fg.edit();rf.putString("NO", noe);rf.commit();
        SharedPreferences.Editor as=tg.edit();as.putString("ID", id);as.commit();
        SharedPreferences.Editor nm=hy.edit();nm.putString("NAME", name);nm.commit();



    }
    public void goclass(String clss){
        try {
            Class nj=Class.forName("com.abijayana.user.almareg."+clss);
            Intent i=new Intent(getApplicationContext(),nj);
            startActivity(i);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
