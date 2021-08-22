package com.miximixi.noleftovers.ui.food;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

// Reference: https://developers.google.com/ml-kit/vision/text-recognition/android#java
public class FoodTextScanner {
    static TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    public static FoodFragment foodFragment;
    static void process(Bitmap bitmap) {
        int rotationDegree = 0;
        InputImage image = InputImage.fromBitmap(bitmap, rotationDegree);

        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                foodFragment.updateFoodList(parseText(visionText));
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.v("debug", e.toString());
                                    }
                                });
    }

    static private List<Food> parseText(Text visionText) {
        List<Food> newFoods = new LinkedList<>();
        for (Text.TextBlock textBlock: visionText.getTextBlocks()){
            for (Text.Line line: textBlock.getLines()) {
                String lineString = line.getText();
                String foodName = matchFood(lineString);
                if (foodName != null) {
                    Food food = new Food(foodName);
                    newFoods.add(food);
                }
            }
        }
        return newFoods;
    }

    static void loadFoodDict() {
        HashMap<String, String> dict = new HashMap<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(foodFragment.getActivity().getAssets().open("food_dict.txt"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                // mLine = "apple Apple"
                String [] stringArray = mLine.split("\\s+");
                // dict.put("apple", "apple");
                dict.put(stringArray[0],stringArray[1]);
            }
        } catch (IOException e) {
            Log.v("error", e.toString());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    foodDict = dict;
                } catch (IOException e) {
                    Log.v("error", e.toString());
                }
            }
        }
    }

    static HashMap<String, String> foodDict = new HashMap<>();

    static private String matchFood(String lineString) {
        String[] itemNames = lineString.split("\\s+");
        for (String itemName: itemNames) {
            if (foodDict.containsKey(itemName.toLowerCase())){
                return foodDict.get(itemName.toLowerCase());
            }
        }
        return null;
    }
}
