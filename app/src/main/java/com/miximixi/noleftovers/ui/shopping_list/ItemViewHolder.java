package com.miximixi.noleftovers.ui.shopping_list;

import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miximixi.noleftovers.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    CheckedTextView checkedTextView;
    ImageButton deleteButton;
    ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.checkedTextView = (CheckedTextView) itemView.findViewById(R.id.item_checked_textview);
        this.deleteButton = (ImageButton) itemView.findViewById(R.id.delete_shopping_item);
    }
}
