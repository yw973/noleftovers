package com.miximixi.noleftovers.ui.shopping_list;

public class ShoppingListItem {
    String name;
    boolean checked;

    public ShoppingListItem(String name){
        this.name = name;
        this.checked = false;
    }

    public ShoppingListItem(String name, boolean isChecked){
        this.name = name;
        this.checked = isChecked;
    }

    public String toString(){
        if (checked) {
            return name + " checked";
        } else {
            return name + " not_checked";
        }
    }
}

