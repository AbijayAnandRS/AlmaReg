package com.abijayana.user.almareg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28-07-2016.
 */
public class comment_adapter  extends ArrayAdapter<nwsfeed>{

    Context context;
    int resource;
    ArrayList<nwsfeed> objects;
    public comment_adapter(Context context, int resource, ArrayList<nwsfeed> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vw;
        if(convertView==null)
        {
            LayoutInflater lf=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=lf.inflate(resource,null);
            vw=new ViewHolder();
            vw.tv1=(TextView)convertView.findViewById(R.id.tv1rty);
            vw.tv2=(TextView)convertView.findViewById(R.id.tv2yhu);
            vw.pv=(ProfilePictureView)convertView.findViewById(R.id.imagenew1);
            convertView.setTag(vw);



        }
        else
        {
            vw=(ViewHolder)convertView.getTag();
        }
        vw.tv1.setText(objects.get(position).getCidnme());
        vw.tv2.setText(objects.get(position).getComment());
        vw.pv.setProfileId(objects.get(position).getIdcomment());



        return convertView;
    }



    static class ViewHolder{

        ProfilePictureView pv;
        TextView tv1;
        TextView tv2;


    }
}
