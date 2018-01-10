package com.cloudsys.smashintl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import com.cloudsys.smashintl.R;

import java.util.ArrayList;

/**
 * Created by azmin on 9/8/16.
 */
public class SpinnerRegistrationAdapter extends ArrayAdapter implements SpinnerAdapter{

    Context mContext;
    ArrayList<RegistrationSpinnerItem> list;


    public SpinnerRegistrationAdapter(Context context, int resource, ArrayList<RegistrationSpinnerItem> list) {
        super(context, resource, list);
        this.mContext = context;
        this.list = list;
        RegistrationSpinnerItem item = new RegistrationSpinnerItem();
        list.add(0, item);

    }




    @Override
    public int getCount() {
        return list.size();
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_header, null);
        return view;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view = null;
        TextView mTextView = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, null);
        mTextView = (TextView) view.findViewById(R.id.TVitem);
//        mTextView.setText(list.get(position).getId());

        if(position%2==0){
            view.setBackgroundResource(R.drawable.selector_ash_to_dark_ash_bottom_strock);
        }else {
            view.setBackgroundResource(R.drawable.selector_light_ash_to_dark_ash_bottom_strock);
        }


        return view;
    }
}
