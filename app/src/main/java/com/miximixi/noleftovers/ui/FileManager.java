package com.miximixi.noleftovers.ui;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.miximixi.noleftovers.ui.food.Food;
import com.miximixi.noleftovers.ui.shopping_list.ShoppingListItem;

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
    static final String SHOPPING_LIST_DATA_PATH = "shopping_list_data.txt";

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
            InputStream inputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            String line;
            while ((line = br.readLine()) != null) {
                dataList.add(line.toString());
            }
            br.close();
        }
        catch (Exception e) { }
        return dataList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static public void saveFoodList(Context context, List<Food> foodList) {
        List<String> stringList = new LinkedList<>();
        for (Food food: foodList){
            stringList.add(food.toString());
        }

        // convert string list to a long long string separated by "\n"
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("food_data.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(String.join("\n",stringList));
            outputStreamWriter.close();
        }
        catch (IOException e) {
            System.out.println("File write failed: " + e.toString());
        }
    }

    // Shopping list file management

    static public List<ShoppingListItem> loadShoppingList(Context context) {
        // Load text data from shopping_list_data.txt
        List<String> dataStrings = loadFile(context, SHOPPING_LIST_DATA_PATH);

        // Parse string into food list
        List<ShoppingListItem> shoppingList = new LinkedList<>();
        for (String data: dataStrings) {
            ShoppingListItem newItem = parseShoppingListString(data);
            shoppingList.add(newItem);
        }
        return shoppingList;
    }

    static private ShoppingListItem parseShoppingListString(String data) {
        String[] shoppingItemData = data.split("\\s+");
        // "Apple checked"
        try {
            String name = shoppingItemData[0];
            String isChecked = shoppingItemData[1];
            if (isChecked.equals("checked")) {
                return new ShoppingListItem(name, true);
            } else {
                return new ShoppingListItem(name, false);
            }
        } catch (Exception e) {
            Log.v("debug", "Error parsing shopping item string.");
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static public void saveShoppingList(Context context, List<ShoppingListItem> shoppingList) {
        List<String> stringList = new LinkedList<>();
        for (ShoppingListItem item: shoppingList){
            stringList.add(item.toString());
        }

        // convert string list to a long long string separated by "\n"
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(SHOPPING_LIST_DATA_PATH, Context.MODE_PRIVATE));
            outputStreamWriter.write(String.join("\n",stringList));
            outputStreamWriter.close();
        }
        catch (IOException e) {
            System.out.println("File write failed: " + e.toString());
        }
    }

}
