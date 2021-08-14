package com.miximixi.noleftovers.ui;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.miximixi.noleftovers.ui.food.Food;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

public class FileManager {
    // Food data format: name, amount, category, expirationDate, addedDate
    // date: mm/dd/yyyy
    // category: string
    static final String FOOD_DATA_PATH = "food_data.txt";

    static public List<Food> loadFoodList(Context context) {
        // Load text data from food_data.txt
        List<String> dataStrings = loadFile(context, FOOD_DATA_PATH);

        // Parse string into food list
        List<Food> foods = new LinkedList<>();
        for (String data: dataStrings) {
            Food newFood = parseFoodString(data);
            foods.add(newFood);
        }
        return foods;
    }

    static private Food parseFoodString(String data) {
        String[] foodData = data.split("\\s+");
        return new Food(foodData);
    }

    private static List<String> loadFile(Context context, String fileName) {
        List<String> dataList = new LinkedList<>();

        try {
            //Log.v("tag","loading");
            InputStream inputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            String line;
            while ((line = br.readLine()) != null) {
                dataList.add(line.toString());
            }
            br.close();
        }
        catch (Exception e) {
            //You'll need to add proper error handling here
            //Log.v("error",e.toString());
        }
        return dataList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static public void saveFoodList(Context context, List<Food> foodList) {
        List<String> stringList = new LinkedList<>();
        for (Food food: foodList){
            stringList.add(food.toString());
        }
        //Log.v("tag","Saving string: ");
        //Log.v("tag",stringList.get(0));
        // convert string list to a long long string separated by "\n"
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("food_data.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(String.join("\n",stringList));
            outputStreamWriter.close();
            //Log.v("tag","write succeed");
            //Log.v("tag", String.join("\n",stringList));
        }
        catch (IOException e) {
            System.out.println("File write failed: " + e.toString());
        }


        // Convert foodList to a string list
        // ["Apple 6 fruit 01/20/2000", ""...]

        // Open/Create the file
        // Write the string data to txt file
        // either concatenate the string to a long string connected by "\n"
        // or write it line by line
        // save the file
    }

}
