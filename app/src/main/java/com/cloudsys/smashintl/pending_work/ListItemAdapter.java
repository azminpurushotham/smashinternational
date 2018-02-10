package com.cloudsys.smashintl.pending_work;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.pending_work.model.Result;
import com.cloudsys.smashintl.utiliti.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by azmin on 11/30/2017.
 */

class ListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnAdapterItemClick listner;
    List<Result> list = new ArrayList<>();
    Context mContext;

    public interface OnAdapterItemClick {
        public void onAdapterItemClick(Result Result, int adapterPosition);
    }


    public ListItemAdapter(List<Result> list, Context viewContext, OnAdapterItemClick listner) {
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
            ((ViewHolder) holder).TVname.setText(list.get(position).getName());
            ((ViewHolder) holder).TVid.setText(list.get(position).getCustomerId());
            ((ViewHolder) holder).TVlocation.setText(list.get(position).getAddress());
            ((ViewHolder) holder).TVamount.setText(list.get(position).getAmount() + " " + list.get(position).getCurrency());
            ((ViewHolder) holder).TVdate.setText(
                    Utilities.getFormatedDate(list.get(position).getDate(),
                            Utilities.REQ_FORMAT,
                            Utilities.SERVER_DATE_FORMAT));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
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
            BTNupdateStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listner.onAdapterItemClick(list.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
