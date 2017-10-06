package com.abijayana.user.almareg;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TabLayout tabLayout;
    static View rootView1;
   static View rootView2;
    LayoutInflater l1,l2;   FloatingActionButton fab;
    public SectionsPagerAdapter mSectionsPagerAdapter;

    public ViewPager mViewPager;
    ArrayList<nwsfeed> list1;
    NewsAdapter adp;
    Firebase fr;String profa,pnme;
    ListView lv;
    long r1;
    SharedPreferences s,d,pol,sp,hj,dfl;
    Button breg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        s=this.getSharedPreferences("SSD",Context.MODE_PRIVATE);
        d=this.getSharedPreferences("DDS", Context.MODE_PRIVATE);
        dfl=this.getSharedPreferences("ASD",Context.MODE_PRIVATE);
        SharedPreferences.Editor gho=dfl.edit();gho.putInt("NUM",2);gho.commit();
        pol = this.getSharedPreferences("ABHAYA", Context.MODE_PRIVATE);profa=pol.getString("ABHA","HAI");
        sp=this.getSharedPreferences("AMMAACHAN", Context.MODE_PRIVATE);pnme=sp.getString("HAI","User");
        hj=this.getSharedPreferences("ACHANAMMA",Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#3b5998"));
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container1);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(mViewPager);

        l1=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        l2=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView2=l2.inflate(R.layout.register,null);
        rootView1=l1.inflate(R.layout.listview,null);
        breg=(Button)rootView2.findViewById(R.id.registerbtn);
        breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goclass("register");
            }
        });
        lv=(ListView)rootView1.findViewById(R.id.lsvw3);
        list1=new ArrayList<nwsfeed>();
        adp=new NewsAdapter(MainActivity.this,R.layout.singlenews,list1);
        lv.setAdapter(adp);
        fr=new Firebase("https://almareg.firebaseio.com/newsfeed");
        fr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                r1 = dataSnapshot.getChildrenCount()-5;
                list1.clear();
                for (long i = dataSnapshot.getChildrenCount() - 1; i >= 0; i--) {
                    nwsfeed abc = new nwsfeed();
                    abc.setHeading(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("Heading").getValue()));
                    abc.setImageurl(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("imgurl").getValue()));
                    long h=dataSnapshot.child(String.valueOf(i)).child("comments").getChildrenCount();
                    abc.setIdcomment(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("comments").child(String.valueOf(h - 1)).child("id").getValue()));
                    abc.setCidnme(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("comments").child(String.valueOf(h - 1)).child("name").getValue()));
                    abc.setComment(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("comments").child(String.valueOf(h - 1)).child("comment").getValue()));
                    abc.setLikes(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("likes").getChildrenCount()));
                    list1.add(abc);


                }
                adp.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        fr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adp.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                adp.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adp.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                adp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                fr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        SharedPreferences.Editor edr = s.edit();
                        edr.putInt("Pos", (int) (dataSnapshot.getChildrenCount() - 1 - position));
                        edr.commit();
                        SharedPreferences.Editor der = d.edit();
                        der.putString("imurl", list1.get(position).getImageurl());
                        der.commit();
                        try {
                            Class g = Class.forName("com.abijayana.user.almareg.image");
                            Intent io = new Intent(MainActivity.this, g);
                            startActivity(io);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }
        });
        lv.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
                return true;
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        View ju= navigationView.inflateHeaderView(R.layout.nav_header_main);

        TextView me=(TextView)ju.findViewById(R.id.textbeg);
        ProfilePictureView prof=(ProfilePictureView)ju.findViewById(R.id.profpicy);
        prof.setProfileId(profa);
        me.setText(pnme);
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
        if (id == R.id.action_settings) gobrowser("https://www.facebook.com/almafiesta");
        else if(id==R.id.youtube)gobrowser("https://www.youtube.com/c/almafiesta");
        else if(id==R.id.twitter)gobrowser("https://twitter.com/almafiesta");
        else if(id==R.id.official)gobrowser("http://www.almafiesta.com");

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.usersas){
          goclass("Abijay");
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static class abi1 extends Fragment {

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
                    //this page does not exists
                    return null;
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

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
                    return "NEWS";
                case 1:
                    return "REGISTER";

            }
            return null;
        }

    }
    // Append more data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void customLoadMoreDataFromApi(int page) {
        // Send an API request to retrieve appropriate data using the offset value as a parameter.
        //  --> Deserialize API response and then construct new objects to append to the adapter
        //  --> Notify the adapter of the changes
    }
    public void goclass(String classname) {
        Intent i;
        try {
            Class g = Class.forName("com.abijayana.user.almareg." + classname);
            i = new Intent(MainActivity.this, g);
            startActivity(i);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
    public void gobrowser(String ser){
try {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(ser));
        startActivity(i);}catch (ActivityNotFoundException e){
    Toast.makeText(MainActivity.this,"Install WebBrowser",Toast.LENGTH_SHORT).show();
    e.printStackTrace();
}


    }


}
