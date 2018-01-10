package com.cloudsys.smashintl.scheduledwork;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloudsys.smashintl.R;

import java.util.ArrayList;

/**
 * Created by azmin on 11/30/2017.
 */

class ListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnAdapterItemClick listner;
    ArrayList<ServicesPojo> list = new ArrayList<>();
    Context mContext;

    public interface OnAdapterItemClick {
        public void onAdapterItemClick(ServicesPojo servicesPojo, int adapterPosition);
    }


    public ListItemAdapter(ArrayList<ServicesPojo> list, Context viewContext, OnAdapterItemClick listner) {
        this.list = list;
        this.mContext = viewContext;
        this.listner = listner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scheduledwork, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView TVname, TVid, TVlocation, TVamount, TVdate;
        Button BTNupdateStatus;
        View mView;

        public ViewHolder(View inflate) {
            super(inflate);
            TVname = (TextView) inflate.findViewById(R.id.TVname);
            TVid = (TextView) inflate.findViewById(R.id.TVid);
            TVlocation = (TextView) inflate.findViewById(R.id.TVlocation);
            TVamount = (TextView) inflate.findViewById(R.id.TVamount);
            TVdate = (TextView) inflate.findViewById(R.id.TVdate);
            BTNupdateStatus = (Button) inflate.findViewById(R.id.BTNupdateStatus);
            this.mView = inflate;
            inflate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listner.onAdapterItemClick(list.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
