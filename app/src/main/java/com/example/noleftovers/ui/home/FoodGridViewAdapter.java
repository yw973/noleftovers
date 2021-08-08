package com.example.noleftovers.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.noleftovers.R;

import java.util.List;
import java.util.zip.Inflater;

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
        TextView textView = view.findViewById(R.id.food_label);
        textView.setText(food.name);
        return view;
    }
}
