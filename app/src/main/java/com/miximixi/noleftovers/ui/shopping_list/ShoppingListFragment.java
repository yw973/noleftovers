package com.miximixi.noleftovers.ui.shopping_list;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miximixi.noleftovers.R;
import com.miximixi.noleftovers.databinding.FragmentShoppingListBinding;
import com.miximixi.noleftovers.ui.food.Food;
import com.miximixi.noleftovers.ui.food.FoodGridViewAdapter;

import java.util.LinkedList;
import java.util.List;

// TODO:
// 5. Implement a recycler view (table), enable checking - todolist style
// 6. Interaction between shopping list and food list
// 4. Enable selecting/editing after scanning
// 1. UI for displaying freshness, days until expiration (medium)
// dropdown list, date expired foods come first
// save data and load in the same file
// check a food when get the item at the same time
// erase/keep the food from the list and add them into the "fridge"


// 3. Expand food database (too meat), add better placeholder to not found

public class ShoppingListFragment extends Fragment {

    private ShoppingListViewModel notificationsViewModel;
    private FragmentShoppingListBinding binding;
    private ShoppingListAdapter shoppingListAdapter;
    private List<ShoppingListItem> shoppingList = new LinkedList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(ShoppingListViewModel.class);

        binding = FragmentShoppingListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadShoppingList();
        initRecyclerView(root);
        registerAddButton(root);

        return root;
    }

    private void loadShoppingList() {
        ShoppingListItem apple = new ShoppingListItem("Apple");
        ShoppingListItem banana = new ShoppingListItem("Banana");
        ShoppingListItem pork = new ShoppingListItem("Pork");
        ShoppingListItem cherry = new ShoppingListItem("Cherry");
        shoppingList.add(apple);
        shoppingList.add(banana);
        shoppingList.add(pork);
        shoppingList.add(cherry);
    }

    private void initRecyclerView(View view) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView shoppingListRecyclerView = view.findViewById(R.id.shopping_list);
        shoppingListAdapter = new ShoppingListAdapter(getContext(), shoppingList);
        shoppingListRecyclerView.setLayoutManager(llm);
        shoppingListRecyclerView.setAdapter(shoppingListAdapter);
    }

    private void registerAddButton(View view){
        Button btn = (Button) view.findViewById(R.id.add_shopping_item_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                addButtonPressed(dialog);
            }
        });
    }

    private void addButtonPressed(final Dialog dialog) {
        dialog.setContentView(R.layout.custom_dialogue_add_shopping_item);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btn = (Button) dialog.findViewById(R.id.add_item_shopping);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the textview
                // Get the text inside
                // Create a ShoppingListItem obejct with the text
                // Add it to shoppingList
                // Reload recycler view
                // Dismiss dialog

                String name = ((TextView) dialog.findViewById(R.id.input_item)).getText().toString();


                ShoppingListItem item= new ShoppingListItem(name);
                shoppingList.add(item);
                shoppingListAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}