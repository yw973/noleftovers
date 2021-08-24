package com.miximixi.noleftovers.ui.food;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.miximixi.noleftovers.ui.FileManager;

import java.util.LinkedList;
import java.util.List;

public class FoodViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public List<Food> foodList = new LinkedList<>();
    public Context mContext;

    public FoodViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public void loadFood() {
        foodList = FileManager.loadFoodList(mContext);

//        Food apple = new Food("Apple", 2, null, null);
//        foodList.add(apple);
//
//        Food banana = new Food("Banana", 5, null, null);
//        foodList.add(banana);

    }

    public void updateFoodList(List<Food> newFoods) {
        for (Food newFood: newFoods) {
            boolean added = false;
            for (Food oldFood: foodList) {
                if (newFood.name.toLowerCase().equals(oldFood.name)) {
                    oldFood.amount += newFood.amount;
                    added = true;
                }
            }
            if (!added) {
                foodList.add(newFood);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveFoodList() {
        FileManager.saveFoodList(mContext, foodList);
    }

    public LiveData<String> getText() {
        return mText;
    }
}