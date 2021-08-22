package com.miximixi.noleftovers.ui.food;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.miximixi.noleftovers.R;
import com.miximixi.noleftovers.databinding.FragmentFoodBinding;
import com.miximixi.noleftovers.ui.FileManager;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

public class FoodFragment extends Fragment {

    private FoodViewModel foodViewModel;
    private FragmentFoodBinding binding;
    private FoodGridViewAdapter foodViewAdapter;
    private List<Food> foodList = new LinkedList<>();

    private ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodViewModel =
                new ViewModelProvider(this).get(FoodViewModel.class);

        binding = FragmentFoodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageView = (ImageView) root.findViewById(R.id.image_view);

        FoodTextScanner.foodFragment = this;
        FoodTextScanner.loadFoodDict();

        registerAddButton(root);
        registerCameraButton(root);
        loadFood();
        initGridView(root);
        //testTextRecognization(R.drawable.wegmanstest);
        return root;
    }

    private void registerAddButton(View view) {
        Button btn = (Button) view.findViewById(R.id.add_food_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // your handler code here
                final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                addButtonPressed(dialog);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addButtonPressed(final Dialog dialog) {
        dialog.setContentView(R.layout.custom_dialogue_add_food);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btn = (Button) dialog.findViewById(R.id.submit_food);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] data = new String[5];
                data[0] = ((TextView) dialog.findViewById(R.id.input_food_name)).getText().toString();
                data[1] = ((TextView) dialog.findViewById(R.id.input_amount)).getText().toString();
                data[2] = "whatever";
                data[3] = ((TextView) dialog.findViewById(R.id.input_expired_date)).getText().toString();

                Food newFood = new Food(data);
                foodList.add(newFood);
                foodViewAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void testTextRecognization(int fileName) {
        Bitmap receipt = BitmapFactory.decodeResource(getResources(), fileName);
        FoodTextScanner.process(receipt);
    }

    private void loadFood() {
        foodList = FileManager.loadFoodList(getContext());

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
        foodViewAdapter.notifyDataSetChanged();
    }

    private void initGridView(View view) {
        GridView foodGridView = view.findViewById(R.id.food_grid_view);
        foodViewAdapter = new FoodGridViewAdapter(getContext(), foodList);
        foodGridView.setAdapter(foodViewAdapter);
        foodGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food food = foodList.get(i);
                final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                dialog.setContentView(R.layout.food_detail_dialogue);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView amountTextView = (TextView) dialog.findViewById(R.id.food_amount_dialog);
                amountTextView.setText(Integer.toString(food.amount));
                TextView expirationDateTextView = (TextView) dialog.findViewById(R.id.expired_date_dialog);
                expirationDateTextView.setText(food.dateToString(food.dateExpired));

                ImageButton addButton = (ImageButton) dialog.findViewById(R.id.add_food_amount_button);
                addAmountChangeListener(addButton, amountTextView, food, 1);
                ImageButton minusButton = (ImageButton) dialog.findViewById(R.id.subtract_food_amount_button);
                addAmountChangeListener(minusButton, amountTextView, food, -1);

                Button deleteButton = (Button) dialog.findViewById(R.id.delete_food);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foodList.remove(i);
                        foodViewAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    void addAmountChangeListener(ImageButton btn, TextView amountTextView, Food food, int sign) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food.amount = food.amount+1*sign;
                amountTextView.setText(Integer.toString(food.amount));
            }
        });
    }
    private static final int REQUEST_IMAGE_CAPTURE=101;

    private void registerCameraButton(View view) {
        ImageButton btn = (ImageButton) view.findViewById(R.id.camera_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                }
                Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //if (imageTakeIntent.resolveActivity(activity.getPackageManager()) != null) {
                startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        FileManager.saveFoodList(getContext(), foodList);
    }
}