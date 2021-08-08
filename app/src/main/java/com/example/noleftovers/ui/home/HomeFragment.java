package com.example.noleftovers.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.noleftovers.MainActivity;
import com.example.noleftovers.R;
import com.example.noleftovers.databinding.FragmentHomeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private FoodGridViewAdapter foodViewAdapter;
    private List<Food> foodList = new LinkedList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        registerAddButton(root);
        loadFood();
        initGridView(root);

        return root;
    }

    private void registerAddButton(View view) {
        Button btn = (Button) view.findViewById(R.id.add_food_button);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // your handler code here
                System.out.println("Button pressed");
                final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                addButtonPressed(dialog);
            }
        };
        btn.setOnClickListener(listener);

    }

    private void addButtonPressed(final Dialog dialog) {
        dialog.setContentView(R.layout.custom_dialogue_add_food);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btn = (Button) dialog.findViewById(R.id.submit_food);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodName = ((TextView) dialog.findViewById(R.id.input_food_name)).getText().toString();
                int amount = Integer.parseInt(((TextView) dialog.findViewById(R.id.input_amount)).getText().toString());

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String dateString = ((TextView) dialog.findViewById(R.id.input_expired_date)).getText().toString();
                Date dateExpired = null;
                try {
                    dateExpired = formatter.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Food newFood = new Food(foodName, amount, null, dateExpired);
                foodList.add(newFood);
                foodViewAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void loadFood() {
        Food apple = new Food("Apple", 2, null, null);
        foodList.add(apple);

        Food banana = new Food("Banana", 5, null, null);
        foodList.add(banana);
    }

    private void initGridView(View view) {
        GridView foodGridView = view.findViewById(R.id.food_grid_view);
        foodViewAdapter = new FoodGridViewAdapter(getContext(), foodList);
        foodGridView.setAdapter(foodViewAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}