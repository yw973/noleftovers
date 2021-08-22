package com.miximixi.noleftovers.ui.food;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miximixi.noleftovers.R;

import java.util.List;

public class FoodGridViewAdapter extends BaseAdapter {
    Context mContext;
    List<Food> foodList;
    LayoutInflater inflater;
    //TODO: Food class, categories

    FoodGridViewAdapter(Context context, List<Food> foodList){
        this.mContext = context;
        this.foodList = foodList;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.food_gric_cell, null);
        }
        Food food = foodList.get(i);
        ImageView imageView = view.findViewById(R.id.food_icon);
        try {
            int imgId = mContext.getResources().getIdentifier(food.name.toLowerCase(), "drawable", mContext.getPackageName());
            Drawable img = mContext.getResources().getDrawable(imgId);
            imageView.setImageDrawable(img);
        } catch (Exception e) {

        }

        TextView textView = view.findViewById(R.id.food_label);
        textView.setText(food.name);
        return view;
    }
}
