package com.abijayana.user.almareg;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;
import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28-07-2016.
 */

public class NewsAdapter extends ArrayAdapter<nwsfeed> {
    Firebase fr;
    Context context;
    int resource;
    ArrayList<nwsfeed> objects;Animation anime;
    public NewsAdapter(Context context, int resource, ArrayList<nwsfeed> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vw;
        anime=AnimationUtils.loadAnimation(getContext(),R.anim.alpha);
        if(convertView==null){

            LayoutInflater lf=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=lf.inflate(resource,null);
            vw=new ViewHolder();
            vw.tv=(TextView)convertView.findViewById(R.id.textast);
            vw.iv=(ImageView)convertView.findViewById(R.id.image1);
            vw.pv=(ProfilePictureView)convertView.findViewById(R.id.imagenew2);
            vw.tv2=(TextView)convertView.findViewById(R.id.tv1rtya);
            vw.tv3=(TextView)convertView.findViewById(R.id.tv2yhua);
            //vw.lk=(LinearLayout)convertView.findViewById(R.id.lnrlio);
            vw.like=(TextView)convertView.findViewById(R.id.liker);

            convertView.setTag(vw);


        }
        else vw=(ViewHolder)convertView.getTag();



        vw.tv.setText(objects.get(position).getHeading());
        if(objects.get(position).getImageurl().compareTo("")==0){}
        else {Picasso.with(context).load(objects.get(position).getImageurl()).placeholder(R.drawable.rotate).into(vw.iv);}
        if(objects.get(position).getIdcomment().compareTo("null")==0)vw.pv.setVisibility(View.INVISIBLE);
        else {vw.pv.setVisibility(View.VISIBLE);vw.pv.setProfileId(objects.get(position).getIdcomment());}
        if(objects.get(position).getCidnme().compareTo("null")==0){vw.tv2.setText("");vw.tv3.setText("");}
        else{
        vw.tv2.setText(objects.get(position).getCidnme());
        vw.tv3.setText(objects.get(position).getComment());}
        vw.like.setText(objects.get(position).getLikes());





       convertView.startAnimation(anime);

        return convertView;

    }

    public class ViewHolder{
        TextView tv;
        ImageView iv;
        ProfilePictureView pv;
        TextView tv2;
        TextView tv3;
        LinearLayout lk;
        TextView like;



    }



}
