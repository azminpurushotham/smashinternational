package com.cloudsys.smashintl.workdetailview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cloudsys.smashintl.R;

import java.util.ArrayList;

/**
 * Created by Nazif on 03-01-2018.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> data;
    LayoutInflater inflater;

    /*************  CustomAdapter Constructor *****************/
    public CustomSpinnerAdapter(Context context,
                                int textViewResourceId,
                                ArrayList objects) {
        super(context, textViewResourceId, objects);
        /********** Take passed values **********/
        mContext = context;
        data = objects;
        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.item_reason_spinner, parent, false);

        /***** Get each Model object from Arraylist ********/
        TextView tvSpinnerItem = (TextView) row.findViewById(R.id.tvSpinnerItem);
        tvSpinnerItem.setText(data.get(position));
        return row;
    }
}
