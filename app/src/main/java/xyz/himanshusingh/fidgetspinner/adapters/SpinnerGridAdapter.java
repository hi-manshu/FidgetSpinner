package xyz.himanshusingh.fidgetspinner.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import xyz.himanshusingh.fidgetspinner.R;

/**
 * Created by @himanshu on 5/5/17.
 */

public class SpinnerGridAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> spinnerIds;
    public SpinnerGridAdapter(Context context, ArrayList<Integer> spinnerIds) {
        this.context = context;
        this.spinnerIds = spinnerIds;
    }
    @Override
    public int getCount() {
        return spinnerIds.size();
    }
    @Override
    public Object getItem(int position) {
        return spinnerIds.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.fidgetSpinner);
        imageView.setImageResource(spinnerIds.get(position));
        return v;

glide
//        final ImageView myImageView;
//        if (convertView == null) {
//            myImageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
//        } else {
//            myImageView = (ImageView) convertView;
//        }
//
//        int url = spinnerIds.get(position);
//
//        Glide
//                .with(context)
//                .load(url)
//                .centerCrop()
//                .placeholder(R.drawable.fidgetlogo)
//                .into(myImageView);
//
//        return myImageView;

    }
}
