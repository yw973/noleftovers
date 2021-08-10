package com.example.noleftovers.ui.home;

import android.widget.TextView;

import com.example.noleftovers.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Food {
    static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    String name;
    int amount;
    Date addedDate;
    Date dateExpired;
    Date daysLeft;
    Category category;

    public Food(String name, int amount, Category category, Date dateExpired) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.addedDate = getToday();
        this.dateExpired = dateExpired;
    }

    public Food(String[] data) {
        this.name = data[0];
        this.amount = Integer.parseInt(data[1]);
        this.category = stringToCategory(data[2]);
        this.dateExpired = parseDate(data[3]);
        try {
            this.addedDate = parseDate(data[4]);
        } catch (Exception e) {
            this.addedDate = getToday();
        };
    }

    static public Date parseDate(String dateString){
        Date dateExpired = null;
        try {
            dateExpired = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateExpired;
    }

    static public Category stringToCategory(String categoryString) {
        switch (categoryString.toLowerCase()) {
            case "fruit":
                return Category.FRUIT;
            case "vegetable":
                return Category.VEGETABLE;
            case "meat":
                return Category.MEAT;
            case "seafood":
                return Category.SEAFOOD;
            case "dairy":
                return Category.DAIRY;
            default:
                return null;
        }
    }

    static public String categoryToString(Category category) {
        switch (category) {
            case FRUIT:
                return "fruit";
            case VEGETABLE:
                return "vegetable";
            case MEAT:
                return "meat";
            case SEAFOOD:
                return "seafood";
            case DAIRY:
                return "dairy";
            default:
                return null;
        }
    }

    private Date getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /* Return the number of days remaining before expiration. */
    public int getDaysLeft() {
        long durationL = dateExpired.getTime()-getToday().getTime();
        int duration = (int) ((durationL / (1000 * 60 * 60 * 24)));
        return duration;
    }

    public void updateAmount(int change) {
        amount += change;
    }

    public String toString() {
        String name = this.name; // "Apple"
        String amount = Integer.toString(this.amount); //"5"
        String category = categoryToString(this.category); // "fruit"
        String expiredDate = dateToString(this.dateExpired);
        String dateAdded = dateToString((this.addedDate));
        return name+" "+amount+" "+category+" "+expiredDate+" "+dateAdded; //"Apple 5 fruit .."
    }
    public String dateToString(Date date){
        return formatter.format(date);
    }

    enum Category {
        FRUIT,
        VEGETABLE,
        MEAT,
        SEAFOOD,
        DAIRY
    }
}
