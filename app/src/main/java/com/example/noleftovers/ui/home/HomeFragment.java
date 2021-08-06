package com.example.noleftovers.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.noleftovers.R;
import com.example.noleftovers.databinding.FragmentHomeBinding;

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

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        textView.setText("Hello");

        loadFood();
        initGridView(root);

        return root;


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