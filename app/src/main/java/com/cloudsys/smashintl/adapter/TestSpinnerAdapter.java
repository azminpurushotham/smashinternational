package com.cloudsys.smashintl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.pojo.Home;

import java.util.ArrayList;

/**
 * Created by azmin on 9/8/16.
 */
public class TestSpinnerAdapter extends ArrayAdapter implements SpinnerAdapter{

    Context mContext;
    ArrayList<Home> list;


    public TestSpinnerAdapter(Context context, int resource, ArrayList<Home> list) {
        super(context, resource, list);
        this.mContext = context;
        this.list = list;
        Home Home = new Home();
        list.add(0, Home);

    }




    @Override
    public int getCount() {
        return 5;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test, null);
        return view;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = null;
        TextView mTextView = null;

//        if (position % 2 == 0) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_location_item_odd, null);
//            mTextView = (TextView) view.findViewById(R.id.TV_locaton);
//        } else {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_location_item_even, null);
//            mTextView = (TextView) view.findViewById(R.id.TV_locaton);
//        }
//
//
//        if (position == 0) {
//            mTextView.setText("Select Location");
//        } else {
////            mTextView.setText(list.get(position).getLocation());
//        }


        return view;
    }
}
