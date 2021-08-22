package com.miximixi.noleftovers.ui.shopping_list;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.miximixi.noleftovers.R;
import com.miximixi.noleftovers.ui.food.Food;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ItemViewHolder> {
    Context mContext;
    List<ShoppingListItem> shoppingItemList;
    LayoutInflater inflater;

    ShoppingListAdapter(Context context, List<ShoppingListItem> shoppingItemList){
        this.mContext = context;
        this.shoppingItemList = shoppingItemList;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ShoppingListItem item = shoppingItemList.get(position);
        CheckedTextView checkedTextView = holder.checkedTextView;
        checkedTextView.setText(item.name);
        checkedTextView.setChecked(item.checked);

        checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.checked) {
                    item.checked = false;
                    checkedTextView.setChecked(false);
                    checkedTextView.setPaintFlags(checkedTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                } else {
                    item.checked = true;
                    checkedTextView.setChecked(true);
                    checkedTextView.setPaintFlags(checkedTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingItemList.remove(holder.getLayoutPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingItemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView checkedTextView;
        ImageButton deleteButton;
        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkedTextView = (CheckedTextView) itemView.findViewById(R.id.item_checked_textview);
            this.deleteButton = (ImageButton) itemView.findViewById(R.id.delete_shopping_item);


        }
    }

}
