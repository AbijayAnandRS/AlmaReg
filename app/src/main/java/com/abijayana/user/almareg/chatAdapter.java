package com.abijayana.user.almareg;

import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 01-08-2016.
 */

public class chatAdapter extends ArrayAdapter<nwsfeed> {

    Context context;
    int resource;
    ArrayList<nwsfeed> objects;
    public chatAdapter(Context context, int resource, ArrayList<nwsfeed> objects) {
        super(context,resource,objects);


        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        final ViewHolder vw;

        if(convertView==null)
        {
            LayoutInflater lf=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=lf.inflate(resource,null);
            vw=new ViewHolder();
            vw.tv=(TextView)convertView.findViewById(R.id.singlechat);
            vw.l1=(LinearLayout)convertView.findViewById(R.id.chatlayout);

            convertView.setTag(vw);



        }
        else{

            vw=(ViewHolder)convertView.getTag();
        }
        vw.tv.setText(objects.get(position).getName());
        int y=objects.get(position).getChatnumber();
        if(y==2)
        {
            vw.l1.setBackgroundResource(R.drawable.rnded);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity= Gravity.RIGHT;
            vw.l1.setLayoutParams(params);

            LinearLayout.LayoutParams paramsa=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            paramsa.setMargins(13,0,30,0);
            vw.tv.setLayoutParams(paramsa);

        }
        else if(y==1)
        {

            vw.l1.setBackgroundResource(R.drawable.uiop);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity= Gravity.LEFT;
            vw.l1.setLayoutParams(params);
            LinearLayout.LayoutParams paramsa=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            paramsa.setMargins(30,0,13,0);
            vw.tv.setLayoutParams(paramsa);
        }


        return convertView;
    }


    static class ViewHolder{
        TextView tv;
        LinearLayout l1;


    }
}


