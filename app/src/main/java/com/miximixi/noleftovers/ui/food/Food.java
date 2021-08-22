package com.miximixi.noleftovers.ui.food;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Food {
    static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    String name;
    int amount;
    Date addedDate;
    Date dateExpired;
    Date daysLeft;
    Category category;//a new constructor, and a new database

    public Food(String name, int amount, Category category, Date dateExpired) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.addedDate = getToday();
        this.dateExpired = dateExpired;
    }

    public Food(String name) {
        this.name = name;
        this.amount = 1;
        this.addedDate = getToday();
        this.dateExpired = getRelativeDate(addedDate,7);
    }

    public Food(String[] data) {
        this.name = data[0];
        try {
            this.amount = Integer.parseInt(data[1]);
        } catch (Exception e) {
            this.amount = 1;
        }

        this.category = stringToCategory(data[2]);
        this.dateExpired = parseDate(data[3]);
        try {
            this.addedDate = parseDate(data[4]);
        } catch (Exception e) {
            this.addedDate = getToday();
        };
        if (dateExpired == null) {
            this.dateExpired = getRelativeDate(addedDate,7);
        }
    }

    static public Date parseDate(String dateString){
        formatter.setLenient(false);
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
        if (category == null) {
            return null;
        }
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

    /** Return date = currentDate + numOfDaysDifferent.
     *  E.g. if currentDate is 08/20/2021, numOfDaysDifferent is 5, then the output
     *  is 08/25/2021. */
    private Date getRelativeDate(Date currentDate, int numOfDaysDifferent) {
        long timeTillNow = currentDate.getTime();
        long totalTime = timeTillNow + numOfDaysDifferent*(1000 * 60 * 60 * 24);
        Date date = new Date(totalTime);
        return date;
    }

    public void updateAmount(int change) {
        amount += change;
    }

    public String toString() {
        String name = this.name; // "Apple"
        String amount = Integer.toString(this.amount); //"5"
        String category = categoryToString(this.category); // "fruit"
        String expiredDate = dateToString(this.dateExpired);
        String dateAdded = dateToString(this.addedDate);
        return name+" "+amount+" "+category+" "+expiredDate+" "+dateAdded; //"Apple 5 fruit .."
    }
    public String dateToString(Date date){
        if (date == null){
            return "date-unknown";
        }
        return formatter.format(date);
    }

    enum Category {
        FRUIT,
        VEGETABLE,
        MEAT,
        SEAFOOD,
        DAIRY;

    }
}
