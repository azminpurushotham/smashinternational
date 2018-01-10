package com.cloudsys.smashintl.home;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloudsys.smashintl.R;

import java.util.ArrayList;

/**
 * Created by azmin on 11/30/2017.
 */

class HomeServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnAdapterItemClick listner;
    ArrayList<ServicesPojo> list = new ArrayList<>();
    Context mContext;

    public interface OnAdapterItemClick {
        public void onAdapterItemClick(ServicesPojo servicesPojo, int adapterPosition);
    }


    public HomeServicesAdapter(ArrayList<ServicesPojo> list, Context viewContext, OnAdapterItemClick listner) {
        this.list = list;
        this.mContext = viewContext;
        this.listner = listner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            if (list.get(position).isSelected()) {
                Glide.with(mContext)
                        .load(list.get(position)
                                .getImageURL()).into(((ViewHolder) holder).mImageView);
                ((ViewHolder) holder).mTextView.setText(list.get(position).getWorkOrderCategoryName());

//                ((ViewHolderQuestionsOptions) holder).mTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                ((ViewHolder) holder).mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                ((ViewHolder) holder).mImageView.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary),
                        PorterDuff.Mode.OVERLAY);
//                ((ViewHolder) holder).mView.setBackgroundResource(R.drawable.shape_solid_colorprimary_darkblue_strock);

            } else {
                Glide.with(mContext)
                        .load(list.get(position)
                                .getImageURL()).into(((ViewHolder) holder).mImageView);
                ((ViewHolder) holder).mTextView.setText(list.get(position).getWorkOrderCategoryName());
                ((ViewHolder) holder).mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                ((ViewHolder) holder).mTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                ((ViewHolder) holder).mImageView.setColorFilter(ContextCompat.getColor(mContext, R.color.transparent),
                        PorterDuff.Mode.ADD);
//                ((ViewHolder) holder).mView.setBackgroundResource(R.drawable.shape_solid_white_ash_strock);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;
        TextView mTextView;
        View mView;

        public ViewHolder(View inflate) {
            super(inflate);
//            mImageView = (ImageView) inflate.findViewById(R.id.mImageView);
//            mTextView = (TextView) inflate.findViewById(R.id.mTextView);
            this.mView = inflate;
            inflate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listner.onAdapterItemClick(list.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
