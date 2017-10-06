package com.abijayana.user.almareg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by user on 26-07-2016.
 */
public class login extends AppCompatActivity {
    TextView th;
    LoginButton loginButton;
    CallbackManager vf;

    SharedPreferences pol,sp;Button dfg; String t;
    public static Activity fa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#3F51B5"));
        }
        TextView tv = (TextView) findViewById(R.id.zteam_alma_heading);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/robothin.ttf");
        tv.setTypeface(face);



        loginButton=(LoginButton)findViewById(R.id.buttonlog);
        dfg=(Button)findViewById(R.id.buttlgin);
        dfg.setVisibility(View.INVISIBLE);

        pol = this.getSharedPreferences("ABHAYA", Context.MODE_PRIVATE);
        sp=this.getSharedPreferences("AMMAACHAN", Context.MODE_PRIVATE);

        t=pol.getString("ABHA", "BOY");
        fa = this;
        loginButton.setReadPermissions("public_profile");

        vf=CallbackManager.Factory.create();



        loginButton.registerCallback(vf, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {


                String s = loginResult.getAccessToken().getUserId().toString();
                SharedPreferences.Editor opi = pol.edit();
                opi.putString("ABHA", s);
                opi.commit();
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            SharedPreferences.Editor uji = sp.edit();
                            uji.putString("HAI", profile2.getName());
                            uji.commit();


                            mProfileTracker.stopTracking();
                        }
                    };
                    mProfileTracker.startTracking();
                } else {
                    Profile profile = Profile.getCurrentProfile();
                    SharedPreferences.Editor uji = sp.edit();
                    uji.putString("HAI", profile.getName());
                    uji.commit();


                }
                dfg.setVisibility(View.VISIBLE);
                LoginManager.getInstance().logOut();
                loginButton.setVisibility(View.INVISIBLE);
                dfg.setText("NEXT");


            }

            @Override
            public void onCancel() {
                dfg.setText("ERROR");


            }

            @Override
            public void onError(FacebookException error) {
                dfg.setText("ERROR");


            }
        });



        dfg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                try {
                    Class g=Class.forName("com.abijayana.user.almareg.createact");
                    i=new Intent(login.this,g);
                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }





            }
        });









    }
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        vf.onActivityResult(requestCode, resultCode, data);
    }


}