package com.abijayana.user.almareg;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;

/**
 * Created by user on 03-08-2016.
 */
public class recieceAdaptr extends ArrayAdapter<nwsfeed> {
    Context context;
    int resource;
    ArrayList<nwsfeed> objects;
    public recieceAdaptr(Context context, int resource, ArrayList<nwsfeed> objects) {
        super(context,resource,objects);


        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        final ViewHoldera vw;

        if(convertView==null)
        {
            LayoutInflater lf=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=lf.inflate(resource,null);
            vw=new ViewHoldera();
            vw.tv1=(TextView)convertView.findViewById(R.id.tv1rtya);
            vw.iv1=(ProfilePictureView)convertView.findViewById(R.id.imagenew1a);
            vw.tv2=(TextView)convertView.findViewById(R.id.tv2yhua);
            vw.tv3=(TextView)convertView.findViewById(R.id.notifya);
            convertView.setTag(vw);



        }
        else{

            vw=(ViewHoldera)convertView.getTag();
        }
        vw.tv1.setText(objects.get(position).getName());
        vw.tv2.setText(objects.get(position).getComment());
        vw.iv1.setProfileId(objects.get(position).getIdcomment());
        long yh=objects.get(position).getNotify();
        if(yh>1)
        {
            vw.tv3.setBackgroundResource(R.mipmap.circle);
            vw.tv3.setText(String.valueOf(yh-1));

        }
        else if(yh<=1){
            vw.tv3.setText("");
            vw.tv3.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }


        return convertView;
    }


    static class ViewHoldera{
        TextView tv1;
        ProfilePictureView iv1;
        TextView tv2;
        TextView tv3;

    }
}
